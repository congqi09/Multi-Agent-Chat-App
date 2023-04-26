package handler;

import protocol.TransmitProtocol;

/**
 * Send messages to other nodes provided by live node lists.
 * Can be used at ui for user send message to someone or broadcast.
 */
public interface CommunicationAPI {

  /**
   * Send a message to the user with given id.
   *
   * @param id  the receiver id
   * @param msg the message
   */
  void send(Long id, TransmitProtocol msg);

  /**
   * Broadcast a message to all live nodes.
   *
   * @param msg message
   */
  void broadcast(TransmitProtocol msg);

//    /**
//     * Broadcast a message exclude the node with given id.
//     *
//     * @param msg the message to nodes
//     * @param id the id of excluded node
//     */
//    void broadcastExclude(TransmitProtocol msg, Long id);

}
