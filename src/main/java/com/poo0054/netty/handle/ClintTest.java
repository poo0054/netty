package com.poo0054.netty.handle;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.junit.Test;

import java.io.IOException;

/**
 * @author zhangzhi
 * @date 2023/2/26
 */
public class ClintTest {
    @Test
    public void clintTest() throws InterruptedException, IOException {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            // 设置客户端参数
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group) // 设置线程组
                    .channel(NioSocketChannel.class) // 设置客户端通道
                    .handler(new ClintChannelInitializer());

            // 链接服务端
            ChannelFuture sync = bootstrap.connect("127.0.0.1", 6669).sync();
            // 关闭
            sync.channel().closeFuture().sync();
        } finally {
            // 优雅关闭
            group.shutdownGracefully();
        }
    }
}
