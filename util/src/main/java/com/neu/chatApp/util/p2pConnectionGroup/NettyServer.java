package com.neu.chatApp.util.p2pConnectionGroup;

import protocol.TransmitProtocol;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import lombok.extern.slf4j.Slf4j;

/**
 * The netty server class is to passively accept connections from others.
 * And add the connected channel to live node list for future manage.
 */
@Slf4j
public class NettyServer {

  private final int port;

  private final ChannelInboundHandler dispatcher;

  public NettyServer(int port, ChannelInboundHandler dispatcher) {
    this.port = port;
    this.dispatcher = dispatcher;
    log.info("Netty server starting at port: [{}]", port);
    new Thread(this::run).start();
  }


  /**
   * Start the manager server and accept for connection.
   *
   */
  public void run() {
    // create parent and child threads
    EventLoopGroup parent = new NioEventLoopGroup();
    EventLoopGroup child = new NioEventLoopGroup();
    try {
      ServerBootstrap bootstrap = new ServerBootstrap();
      bootstrap.group(parent, child)
              // nio to accept connection
              .channel(NioServerSocketChannel.class)
              // the maximum queue length for incoming connection
              .option(ChannelOption.SO_BACKLOG, 1024)
              .option(ChannelOption.SO_REUSEADDR, true)
              .childOption(ChannelOption.SO_KEEPALIVE, true)
              .childHandler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                  ChannelPipeline pipeline = ch.pipeline();
                  // add byte encoder and decoder
                  pipeline.addLast(new ObjectDecoder(1024*1024,
                                  ClassResolvers.weakCachingConcurrentResolver(TransmitProtocol.class.getClassLoader())))
                          .addLast(new ObjectEncoder())
                          // add task dispatcher
                          .addLast(dispatcher);

                }
              });
      // synchronously bind port to the server until succeed or failed
      ChannelFuture channelFuture = bootstrap.bind(port).sync();
      // listening channel shutdown
      channelFuture.channel().closeFuture().sync();
    } catch (InterruptedException e) {
      log.error(e.getMessage());
    } finally {
      // on exit, release system resource
      parent.shutdownGracefully();
      child.shutdownGracefully();
    }
  }
}
