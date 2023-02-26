package com.poo0054.netty.simple;

import org.junit.Test;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @author zhangzhi
 * @date 2023/2/26
 */
public class NettyServerTest {

    @Test
    public void server() throws InterruptedException {
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
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    // 给pipeline 设置处理器
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        socketChannel.pipeline().addLast(new NettyServerHandler());
                    }
                });// 给我们workerGroup的eventLoop 对应的管道处理器
            System.out.println("服务器准备好了!!!!!!!!!!!!!!!");
            // 绑定一个端口 并且同步,生成一个ChannelFuture对象
            ChannelFuture cf = bootstrap.bind(6668).sync();

            // 关闭通道进行监听
            cf.channel().closeFuture().sync();
        } finally {
            // 优雅关闭
            bookGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
