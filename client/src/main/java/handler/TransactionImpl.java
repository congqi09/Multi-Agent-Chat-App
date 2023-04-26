package handler;

import com.neu.chatApp.client.data.ClientData;

import node.Node;
import node.NodeChannel;
import protocol.GeneralType;
import protocol.TransmitProtocol;
import protocol.joinAndLeaveProtocol.JoinAndLeaveType;
import protocol.transactionProtocol.TransactionProtocol;
import protocol.transactionProtocol.TransactionType;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;

import java.util.Iterator;

@Slf4j
public class TransactionImpl implements TransactionAPI {

  public TransactionImpl() {}

  @Override
  public void prepare(Node nodeInfo, JoinAndLeaveType type) {
    TransactionProtocol message = new TransactionProtocol(GeneralType.TRANSACTION, type, TransactionType.PREPARE, nodeInfo);
    log.info("Broadcast the transaction prepare message: " + message);
    TransactionHandler.currentNodeInTransaction = new TransactionProtocol(GeneralType.JOIN_AND_LEAVE, type, nodeInfo);
    log.info("Current node in transaction: " + TransactionHandler.currentNodeInTransaction);
    broadcastExclude(message, nodeInfo.getUserId());
  }

  @Override
  public void accept(Channel channel) {
    TransactionProtocol res = new TransactionProtocol(GeneralType.TRANSACTION, TransactionType.ACCEPT);
    log.info("Responded to the transaction: " + res);
    channel.writeAndFlush(res);
  }

  @Override
  public void abort(Channel channel) {
    TransactionProtocol res = new TransactionProtocol(GeneralType.TRANSACTION, TransactionType.ABORT);
    log.info("Responded to the transaction: " + res);
    channel.writeAndFlush(res);
  }

  @Override
  public void commit(Node nodeInfo, JoinAndLeaveType type) {
    TransactionProtocol request = new TransactionProtocol(GeneralType.TRANSACTION, type, TransactionType.COMMIT, nodeInfo);
    log.info("Sent commit request to the transaction: " + request);
    broadcastExclude(request, nodeInfo.getUserId());
  }

  @Override
  public void drop(Node nodeInfo, JoinAndLeaveType type) {
    TransactionProtocol request = new TransactionProtocol(GeneralType.TRANSACTION, type, TransactionType.DROP, nodeInfo);
    log.info("Sent drop request to the transaction: " + request);
    broadcastExclude(request, nodeInfo.getUserId());
  }

  @Override
  public void ackCommit(Channel channel) {
    TransactionProtocol res = new TransactionProtocol(GeneralType.TRANSACTION, TransactionType.ACK_COMMIT);
    log.info("Responded an ACK message to the transaction: " + res);
    channel.writeAndFlush(res);
  }

  @Override
  public void ackDrop(Channel channel) {
    TransactionProtocol res = new TransactionProtocol(GeneralType.TRANSACTION, TransactionType.ACK_DROP);
    log.info("Responded an ACK message to the transaction: " + res);
    channel.writeAndFlush(res);
  }


  /**
   * Broadcast a message exclude the node with given id.
   *
   * @param msg the message to nodes
   * @param id the id of excluded node
   */
  private void broadcastExclude(TransmitProtocol msg, Long id) {
    Iterator<NodeChannel> allNodes = ClientData.liveNodeList.getAllNodes();
    while (allNodes.hasNext()) {
      NodeChannel next = allNodes.next();
      if (!next.getUserId().equals(id)) {
        log.info("Sent to node [" + next.getNode().getUserId() + "] " + next.getNode().getUserName() + ": " + msg);
        next.getChannel().writeAndFlush(msg);
      }
    }
  }
}
