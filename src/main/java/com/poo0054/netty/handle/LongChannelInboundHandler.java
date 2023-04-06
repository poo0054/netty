package com.poo0054.netty.handle;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author zhangzhi
 * @date 2023/4/6
 */
public class LongChannelInboundHandler extends SimpleChannelInboundHandler<Long> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Long msg) throws Exception {
        System.out.println("收到消息为:" + msg);
    }
}
