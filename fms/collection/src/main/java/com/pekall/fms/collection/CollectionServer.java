package com.pekall.fms.collection;
import java.net.InetSocketAddress;
//import com.pekall.fms.collection.utils.DataConvert;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
//import io.netty.channel.ChannelPipeline;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.ChannelOption;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import org.springframework.context.ApplicationContext;
import com.pekall.fms.collection.codec.AdsbAsterixDecoder;
import com.pekall.fms.collection.codec.CommandHandler;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import io.netty.buffer.ByteBufUtil;
//import io.netty.buffer.Unpooled;
//import io.netty.buffer.ByteBuf;

/**
 * CollectionServer!
 */
public class CollectionServer {
  public static ApplicationContext applicationContext = null;
  public ServerConfig config = null;

  public ServerConfig getConfig() {
    return config;
  }
  public void setConfig(ServerConfig config) {
    this.config = config;
  }

  public void start() throws InterruptedException {
    // Configure the server.
    EventLoopGroup bossGroup = new NioEventLoopGroup(1);
    EventLoopGroup workerGroup = new NioEventLoopGroup();
    try {
      ServerBootstrap bootstrap = new ServerBootstrap();
      bootstrap.group(bossGroup, workerGroup)//
        .channel(NioServerSocketChannel.class)//
        .option(ChannelOption.SO_BACKLOG, 100)
        .handler(new LoggingHandler(LogLevel.INFO))
        .localAddress(new InetSocketAddress(config.getCollectionPort()))//
        .childHandler(
          new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel ch) throws Exception {
              ch.pipeline().addLast("readTimeoutHandler", new ReadTimeoutHandler(config.getCollectionTimeout()))
              .addLast("asterixDecoder", new AdsbAsterixDecoder())
              .addLast("commandHandler",new CommandHandler());
            }
          } //
        );
      ChannelFuture f = bootstrap.bind().sync();
      System.out.println(
        CollectionServer.class.getName()
          + " started and listen on " + f.channel().localAddress()
      );

      f.channel().closeFuture().sync();
    } finally {
      bossGroup.shutdownGracefully();
      workerGroup.shutdownGracefully();
    }
  }
}
