package com.poo0054.netty.handle;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * 1.自定义一个handler 需要继承netty 规定好的handlerAdapter
 * <p>
 * 2. 自定义一个handler
 *
 * @author zhangzhi
 * @date 2023/2/26
 */
public class ServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("读取到消息为:" + msg);
    }
}
