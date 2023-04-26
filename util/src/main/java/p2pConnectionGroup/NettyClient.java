package p2pConnectionGroup;

import protocol.TransmitProtocol;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import lombok.extern.slf4j.Slf4j;

import java.net.SocketTimeoutException;

/**
 * Netty client uses to actively connect to a remote peer.
 */
@Slf4j
public class NettyClient {

  private final Bootstrap bootstrap;
  private final EventLoopGroup group;


  public NettyClient(ChannelInboundHandler dispatcher) {
    log.info("Netty client starting ");
    group = new NioEventLoopGroup();
    bootstrap = new Bootstrap();
    bootstrap.group(group)
            // nio connection
            .channel(NioSocketChannel.class)
            .option(ChannelOption.SO_REUSEADDR, true)
            .option(ChannelOption.SO_KEEPALIVE, true)
            .handler(new ChannelInitializer<SocketChannel>() {
              @Override
              public void initChannel(SocketChannel ch) throws Exception {
                ChannelPipeline pipeline = ch.pipeline();
                // add byte encoder and decoder
                pipeline.addLast(new ObjectDecoder(1024*1024, ClassResolvers.weakCachingConcurrentResolver(TransmitProtocol.class.getClassLoader())))
                        .addLast(new ObjectEncoder())
                        // add task dispatcher
                        .addLast(dispatcher);
              }
            });

  }


  /**
   * Synchronously Connect to a remote peer with given hostname and port.
   * If success the io channel of the connection will be returned.
   * otherwise, throw timeout exception.
   *
   * @param hostname hostname
   * @param port port
   * @return the channel of the io connection
   * @throws SocketTimeoutException when failed to connect within limited time
   */
  public Channel connectTo(String hostname, int port) throws SocketTimeoutException {
    log.info("Connect to hostname: " + hostname + ", port: " + port);
    ChannelFuture channelFuture = bootstrap.connect(hostname, port);
    try {
      // 500 ms timeout
      boolean isCompleted = channelFuture.await(500);
      if (isCompleted) {
        if (channelFuture.isSuccess()) {
          return channelFuture.channel();
        }
      }
    } catch (InterruptedException e) {
      log.error("Connect to the host: " + hostname + ", port: " + port + " was interrupted");
    }
    throw new SocketTimeoutException("Failed to connect to the host: " + hostname + ", port: " + port);
  }
}
