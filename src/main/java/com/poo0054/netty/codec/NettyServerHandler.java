package com.poo0054.netty.codec;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * 1.自定义一个handler 需要继承netty 规定好的handlerAdapter
 * <p>
 * 2. 自定义一个handler
 *
 * @author zhangzhi
 * @date 2023/2/26
 */
public class NettyServerHandler extends SimpleChannelInboundHandler<CodecPo.Study> {


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, CodecPo.Study msg) throws Exception {
        System.out.println(msg.getName());
        System.out.println("读取到的消息为:" + msg.toString());
    }
}
