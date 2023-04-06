package com.poo0054.netty.handle;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;

/**
 * @author zhangzhi
 * @date 2023/4/6
 */
public class ClintChannelInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ch.pipeline().addLast(new LongByteToMessageEncoder());
        //加入编码器
        ch.pipeline().addLast(new ClintHandler());

        // 加入自己的处理器
    }
}
