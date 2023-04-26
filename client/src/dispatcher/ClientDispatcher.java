package dispatcher;

import handler.GeneralCommunicationHandler;
import handler.GeneralEventHandlerAPI;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.client.RestTemplate;
import protocol.TransmitProtocol;
import protocol.generalCommunication.GeneralCommunicationProtocol;

import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


@Slf4j
@ChannelHandler.Sharable
public class ClientDispatcher extends SimpleChannelInboundHandler<TransmitProtocol> {


//    private final GeneralEventHandlerAPI<LeaderElectionProtocol> leaderElectionHandler;

      private final GeneralEventHandlerAPI<GeneralCommunicationProtocol> generalCommunicationHandler;
//
//    private final GeneralEventHandlerAPI<JoinAndLeaveProtocol> joinAndLeaveHandler;
//
//    private final GeneralEventHandlerAPI<TransactionProtocol> transactionEventHandler;
//
//    private final ExecutorService executorService = Executors.newCachedThreadPool();
//
    public ClientDispatcher() {
//        this.leaderElectionHandler = new LeaderElectionHandler();
        this.generalCommunicationHandler = new GeneralCommunicationHandler();
//        this.joinAndLeaveHandler = new JoinAndLeaveHandler();
//        this.transactionEventHandler = new TransactionHandler();
    }
//
//    @Override
//    protected void channelRead0(ChannelHandlerContext ctx, TransmitProtocol msg) throws Exception {
//        switch (msg.getType()) {
//            // dispatch task by types
//            case LEADER_ELECTION:
//                leaderElectionHandler.handle((LeaderElectionProtocol) msg, ctx);
//                break;
//            case GENERAL_COMMUNICATION:
//                generalCommunicationHandler.handle((GeneralCommunicationProtocol) msg, ctx);
//                break;
//            case JOIN_AND_LEAVE:
//                joinAndLeaveHandler.handle((JoinAndLeaveProtocol) msg, ctx);
//                break;
//            case TRANSACTION:
//                transactionEventHandler.handle((TransactionProtocol) msg, ctx);
//                break;
//        }
//    }
//
//    @Override
//    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
//        Node channel = getNodeByChannel(ctx.channel());
//        if (channel == null) {
//            log.info("Channel: " + ctx.channel() + " break the connection");
//            return;
//        }
//        executorService.submit(() -> {
//            try {
//                Thread.sleep(1500);
//            } catch (InterruptedException ignored) {
//            }
//            if (TransactionHandler.currentNodeInTransaction == null) {
//                crashHandler(channel);
//            } else {
//                if (!TransactionHandler.currentNodeInTransaction.getNodeInfo().equals(channel)) {
//                    crashHandler(channel);
//                }
//            }
//        });
//
//    }
//
//    private void crashHandler(Node channel) {
//        if (SharableResource.liveNodeList.isContain(channel.getId())) {
//            if (channel.isLeader()) {
//                log.info("Detected leader node crash: " + channel);
//                SharableResource.liveNodeList.remove(channel.getId());
//            } else {
//                // if not the leader node
//                log.info("Detected a node crash id: " + channel.getId() + ", name: " + channel.getNickname());
//                if (SharableResource.myNode.isLeader()) {
//                    // report to server if my node is leader
//                    new RestTemplate().postForEntity("http://" + SharableResource.serverHostname + ":" + SharableResource.serverHTTPPort + "/user/logout", channel.getId(), Void.class);
//                    log.info("Reported to server");
//                }
//                SharableResource.liveNodeList.remove(channel.getId());
//            }
//        }
//    }
//
//    @Override
//    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
//        log.error(cause.getMessage());
//    }
//
//    private Node getNodeByChannel(Channel channel) {
//        Iterator<NodeChannel> allNodes = SharableResource.liveNodeList.getAllNodes();
//        while (allNodes.hasNext()) {
//            NodeChannel next = allNodes.next();
//            if (next.getChannel().equals(channel)) {
//                return next.getNode();
//            }
//        }
//        return null;
//    }
}