package com.poo0054.netty.httplong;

import com.poo0054.netty.groupchat.Constant;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * http长链接
 *
 * @author zhangzhi
 * @date 2023/4/4
 */
public class HttpLongServerTest {

    public static void main(String[] args) throws InterruptedException {
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup workGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, workGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) {
                            ChannelPipeline pipeline = ch.pipeline();
                            //基于http长连接 使用http编码 解码器
                            pipeline.addLast(new HttpServerCodec());
                            //以块方式写,添加 ChunkedWriteHandler
                            pipeline.addLast(new ChunkedWriteHandler());
                            /*
                            http数据是分段的   HttpObjectAggregator 可以将多个段聚合
                            浏览器发送大量数据时,会发送多次请求
                             */
                            pipeline.addLast(new HttpObjectAggregator(8192));
                            /*
                            对于websocket是以桢的形式处理
                            websocketFrame 有六个子类
                            ws://localhost:7000/xxx 表示请求的uri /hello ->  new WebSocketServerProtocolHandler("/hello")
                            WebSocketServerProtocolHandler 将http协议升级为WebSocket
                             */
                            pipeline.addLast(new WebSocketServerProtocolHandler("/"));
                            //处理业务逻辑
                            pipeline.addLast(new HttpLongHandler());
                        }
                    });
            ChannelFuture channelFuture = serverBootstrap.bind(Constant.PRO).sync();
            channelFuture.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }
    }
}
