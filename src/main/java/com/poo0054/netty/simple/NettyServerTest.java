package com.poo0054.netty.simple;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhangzhi
 * @date 2023/2/26
 */
public class NettyServerTest {

    @Test
    public void server() throws InterruptedException {
        List<Channel> channels = new ArrayList<>();
        // bookGroup 只处理请求 , 真正业务会给workerGroup
        // 默认线程数量为 cpu核数*2
        NioEventLoopGroup bookGroup = new NioEventLoopGroup();
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            // 启动参数
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bookGroup, workerGroup)// 设置俩个线程组
                    .channel(NioServerSocketChannel.class) // 使用NioSocketChannel作为服务器的通道实现
                    .option(ChannelOption.SO_BACKLOG, 128) // 设置线程队列等待连接个数
                    .childOption(ChannelOption.SO_KEEPALIVE, true)// 设置保持活动链接状态
//                    .handler(null)//该handler对应的是bossGroup
                    .childHandler(new ChannelInitializer<SocketChannel>() { //该handler对应的是workerGroup
                        // 给pipeline 设置处理器
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            //每个客户的Channel不同
                            Channel channel = socketChannel.pipeline().channel();
                            channels.add(channel);
                            socketChannel.pipeline().addLast(new NettyServerHandler());
                            socketChannel.pipeline().addLast(new ChannelOutboundHandlerAdapter() {
                                @Override
                                public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
                                    System.out.println("我是 ChannelOutboundHandlerAdapter 准备发送" + msg.toString());
                                    super.write(ctx, msg, promise);
                                }
                            });

                        }
                    });// 给我们workerGroup的eventLoop 对应的管道处理器

            // 绑定一个端口 并且同步,生成一个ChannelFuture对象
            ChannelFuture channelFuture = bootstrap.bind(6669).sync();
            //注册监听器
            channelFuture.addListener(future -> {
                if (future.isSuccess()) {
                    System.out.println("监听端口成功");
                } else {
                    System.out.println("监听端口失败");
                }
            });
            // 关闭通道进行监听
            channelFuture.channel().closeFuture().sync();
        } finally {
            // 优雅关闭
            bookGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }


}
