package handler;

import io.netty.channel.ChannelHandlerContext;
import protocol.TransmitProtocol;

// TODO: server端可能也需要用，所以可能需要移动到别的folder
public interface GeneralEventHandlerAPI <T extends TransmitProtocol> {
    void handle(T protocol, ChannelHandlerContext channelHandlerContext);
}
