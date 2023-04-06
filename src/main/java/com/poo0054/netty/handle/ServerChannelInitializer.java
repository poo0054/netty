package com.poo0054.netty.handle;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;

/**
 * @author zhangzhi
 * @date 2023/4/6
 */
public class ServerChannelInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        //每个客户的Channel不同
        ch.pipeline().addLast(new LongByteToMessageDecoder());
        ch.pipeline().addLast(new LongChannelInboundHandler());
    }
}
