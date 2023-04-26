package com.neu.chatApp.util.p2pConnectionGroup;

import com.neu.chatApp.util.p2pConnectionGroup.NettyClient;
import com.neu.chatApp.util.p2pConnectionGroup.NettyServer;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

import java.net.SocketTimeoutException;

/**
 * Start bi-directed peer to peer connections.
 */
@Slf4j
public class P2PConnectionGroup {

  private final NettyClient client;

  private final NettyServer server;

  /**
   * Construct a peer to peer connection group for actively start and passively receive the incoming connections.
   *
   * @param port the port of the server to be registered
   * @param dispatcher the task dispatcher to dispatch task to different handlers
   */
  public P2PConnectionGroup(int port, ChannelInboundHandler dispatcher) {
    this.client = new NettyClient(dispatcher);
    this.server = new NettyServer(port, dispatcher);
    log.info("Peer to peer service started");
  }

  /**
   * Synchronously Connect to a remote peer of given hostname and port.
   * Wait until the connection completed.
   *
   * @param hostname hostname
   * @param port port
   * @return the channel object of the connected socket
   * @throws SocketTimeoutException when failed to connect within limited time
   */
  public Channel connect(String hostname, int port) throws SocketTimeoutException {
    return client.connectTo(hostname, port);
  }

}
