package com.poo0054.netty.codec;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

import java.nio.charset.StandardCharsets;

/**
 * 1.自定义一个handler 需要继承netty 规定好的handlerAdapter
 * <p>
 * 2. 自定义一个handler
 *
 * @author zhangzhi
 * @date 2023/2/26
 */
public class NettyClintHandler extends ChannelInboundHandlerAdapter {
    /**
     * 通道就绪 触发事件
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        System.out.println("ctx : " + ctx);
        ctx.writeAndFlush(Unpooled.copiedBuffer("hello,server ", CharsetUtil.UTF_8));
    }

    /**
     * 读取客户端发送的消息
     *
     * @param ctx 上下文对象 , 有管道(pipeline) 通道(channel) 链接信息
     * @param msg 消息
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("server ctx = " + ctx);
        // 将msg 转换byteBuf
        ByteBuf buf = (ByteBuf) msg;
        System.out.println("服务端回复消息为:" + buf.toString(StandardCharsets.UTF_8));
        System.out.println("服务端地址为:" + ctx.channel().remoteAddress());
    }

    /**
     * 处理异常.关闭通道
     *
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
