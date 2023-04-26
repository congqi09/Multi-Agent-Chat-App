package dispatcher;

import com.neu.chatApp.client.data.ClientData;

import handler.GeneralCommunicationHandler;
import handler.JoinAndLeaveHandler;
import handler.LeaderElectionHandler;
import handler.TransactionHandler;
import handlerAPI.GeneralEventHandlerAPI;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.client.RestTemplate;

import node.Node;
import node.NodeChannel;
import protocol.TransmitProtocol;
import protocol.generalCommunicationProtocol.GeneralCommunicationProtocol;
import protocol.joinAndLeaveProtocol.JoinAndLeaveProtocol;
import protocol.leaderElectionProtocol.LeaderElectionProtocol;
import protocol.transactionProtocol.TransactionProtocol;

import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


@Slf4j
@ChannelHandler.Sharable
public class ClientDispatcher extends SimpleChannelInboundHandler<TransmitProtocol> {


    private final GeneralEventHandlerAPI<LeaderElectionProtocol> leaderElectionHandler;

    private final GeneralEventHandlerAPI<GeneralCommunicationProtocol> generalCommunicationHandler;

    private final GeneralEventHandlerAPI<JoinAndLeaveProtocol> joinAndLeaveHandler;

    private final GeneralEventHandlerAPI<TransactionProtocol> transactionEventHandler;

    private final ExecutorService executorService = Executors.newCachedThreadPool();

    public ClientDispatcher() {
        this.leaderElectionHandler = new LeaderElectionHandler();
        this.generalCommunicationHandler = new GeneralCommunicationHandler();
        this.joinAndLeaveHandler = new JoinAndLeaveHandler();
        this.transactionEventHandler = new TransactionHandler();
    }

  //  @Override
    protected void messageReceived(ChannelHandlerContext channelHandlerContext, TransmitProtocol transmitProtocol) throws Exception {
        
    }

//    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TransmitProtocol msg) throws Exception {
        switch (msg.getType()) {
            // dispatch task by types
            case LEADER_ELECTION:
                leaderElectionHandler.handle((LeaderElectionProtocol) msg, ctx);
                break;
            case GENERAL_COMMUNICATION:
                generalCommunicationHandler.handle((GeneralCommunicationProtocol) msg, ctx);
                break;
            case JOIN_AND_LEAVE:
                joinAndLeaveHandler.handle((JoinAndLeaveProtocol) msg, ctx);
                break;
            case TRANSACTION:
                transactionEventHandler.handle((TransactionProtocol) msg, ctx);
                break;
        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Node channel = getNodeByChannel(ctx.channel());
        if (channel == null) {
            log.info("Channel: " + ctx.channel() + " break the connection");
            return;
        }
        executorService.submit(() -> {
            try {
                Thread.sleep(1500);
            } catch (InterruptedException ignored) {
            }
            if (TransactionHandler.currentNodeInTransaction == null) {
                crashHandler(channel);
            } else {
                if (!TransactionHandler.currentNodeInTransaction.getNodeInfo().equals(channel)) {
                    crashHandler(channel);
                }
            }
        });

    }

    private void crashHandler(Node channel) {
        if (ClientData.liveNodeList.isContain(channel.getUserId())) {
            if (channel.isLeader()) {
                log.info("Detected leader node crash: " + channel);
                ClientData.liveNodeList.remove(channel.getUserId());
            } else {
                // if not the leader node
                log.info("Detected a node crash id: " + channel.getUserId() + ", name: " + channel.getUserName());
                if (ClientData.myNode.isLeader()) {
                    // report to server if my node is leader
                    new RestTemplate().postForEntity("http://" + ClientData.serverHostname + ":" + ClientData.serverHttpPort + "/user/logout", channel.getUserId(), Void.class);
                    log.info("Reported to server");
                }
                ClientData.liveNodeList.remove(channel.getUserId());
            }
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error(cause.getMessage());
    }

    private Node getNodeByChannel(Channel channel) {
        Iterator<NodeChannel> allNodes = ClientData.liveNodeList.getAllNodes();
        while (allNodes.hasNext()) {
            NodeChannel next = allNodes.next();
            if (next.getChannel().equals(channel)) {
                return next.getNode();
            }
        }
        return null;
    }
}