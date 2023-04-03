package com.poo0054.netty.http;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 * @author zhangzhi
 * @date 2023/4/3
 */
public class HttpServerInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        //向管道加入处理器

        //加入一个HttpServerCodec coder-decoder
        //netty提供的一个http编解码器
        ch.pipeline().addLast(new HttpServerCodec());
        //加入自定义的处理器
        ch.pipeline().addLast(new HttpServerHandler());
    }
}
