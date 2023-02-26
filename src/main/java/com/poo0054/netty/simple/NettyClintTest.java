package com.poo0054.netty.simple;

import org.junit.Test;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @author zhangzhi
 * @date 2023/2/26
 */
public class NettyClintTest {
    @Test
    public void clintTest() throws InterruptedException {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            // 设置客户端参数
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group) // 设置线程组
                .channel(NioSocketChannel.class) // 设置客户端通道
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) {
                        // 加入自己的处理器
                        ch.pipeline().addLast(new NettyClintHandler());
                    }
                });
            System.out.println("客户端启动");

            // 链接服务端
            ChannelFuture sync = bootstrap.connect("127.0.0.1", 6668).sync();
            // 关闭
            sync.channel().closeFuture().sync();
        } finally {
            // 优雅关闭
            group.shutdownGracefully();
        }
    }
}
