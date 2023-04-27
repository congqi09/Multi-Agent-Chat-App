package handler;

import node.Node;
import protocol.joinAndLeaveProtocol.JoinAndLeaveType;

import io.netty.channel.Channel;

/**
 * Only the leader node can start transaction, other nodes only handle transaction event and report to the leader node.
 */
public interface TransactionAPI {

  /**
   * The leader node sends message to all nodes to prepare a transaction
   *
   * @param nodeInfo the node who requested the join or leave
   */
  void prepare(Node nodeInfo, JoinAndLeaveType type);

  /**
   * Node accepts the prepare request and reports to the leader node channel.
   *
   * @param channel the leader node io channel
   */
  void accept(Channel channel);

  /**
   * Node aborts the prepare request and reports to the leader node channel.
   *
   * @param channel the leader node io channel
   */
  void abort(Channel channel);

  /**
   * The leader node requests to commit the node, all nodes will connect to the node and add it to live node list.
   * Then send their self node infos to the node.
   * <p>
   * If the leader node requests for a node leave event, all nodes will remove the node from their live node list.
   *
   * @param nodeInfo the node who requested the join or leave
   */
  void commit(Node nodeInfo, JoinAndLeaveType type);

  /**
   * The leader node requests to drop the node, all nodes should ignore the current transaction and reply ack drop.
   *
   * @param nodeInfo the node who requested the join or leave
   */
  void drop(Node nodeInfo, JoinAndLeaveType type);


  /**
   * After nodes executed the commit, then reply an ack to the leader node.
   *
   * @param channel the leader node io channel
   */
  void ackCommit(Channel channel);

  /**
   * After nodes executed the drop, then reply an ack to leader node.
   *
   * @param channel the leader node io channel
   */
  void ackDrop(Channel channel);
}
