package com.neu.chatApp.util.handlerAPI;

import protocol.TransmitProtocol;
import io.netty.channel.ChannelHandlerContext;

/**
 * A general event handler api handle the events that extends the top protocol.
 *
 * @param <T> the protocol that extends the TransmitProtocol
 */
public interface GeneralEventHandlerAPI<T extends TransmitProtocol> {

  /**
   * Handle the incoming event.
   *
   * @param protocol the message protocol
   * @param ctx channel context
   */
  void handle(T protocol, ChannelHandlerContext ctx);
}
