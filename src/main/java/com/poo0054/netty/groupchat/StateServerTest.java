package com.poo0054.netty.groupchat;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

/**
 * 空闲状态处理器
 *
 * @author zhangzhi
 * @date 2023/4/4
 */
public class StateServerTest {
    @Test
    public void test() throws InterruptedException {
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup workGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, workGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new ChannelInitializer<SocketChannel>() {

                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            //空闲状态处理器
                                /*
                                 observeOutput – 在评估写入空闲时是否应考虑消耗 bytes 。缺省值为 false。
                                 指定时间没有触发状态,就会发送心跳检测包
                                 readerIdleTime – 当 IdleStateEvent 指定时间段内未执行读取时，将触发其状态 IdleState.READER_IDLE 。指定 0 禁用。
                                 writerIdleTime – 当 IdleStateEvent 指定时间段内未执行写入时，将触发其状态 IdleState.WRITER_IDLE 。指定 0 禁用。
                                 allIdleTime – 当 IdleStateEvent 在指定时间段内未执行读取或写入时，将触发其状态 IdleState.ALL_IDLE 。指定 0 禁用。
                                 unitTimeUnit– 时间单位

                                 ------ 当IdleStateHandler触发后,就会传递给下一个处理器 ------
                                 通过调用 userEventTiggered方法
                                 */
                            pipeline.addLast(new IdleStateHandler(3, 5, 7, TimeUnit.SECONDS));
                            //加入自定义处理器
                            pipeline.addLast(new StateHandler());
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
