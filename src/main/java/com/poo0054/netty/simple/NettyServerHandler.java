package com.poo0054.netty.simple;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

/**
 * 1.自定义一个handler 需要继承netty 规定好的handlerAdapter
 * <p>
 * 2. 自定义一个handler
 *
 * @author zhangzhi
 * @date 2023/2/26
 */
public class NettyServerHandler extends ChannelInboundHandlerAdapter {
    /**
     * 读取客户端发送的消息
     *
     * @param ctx 上下文对象 , 有管道(pipeline) 通道(channel) 链接信息
     * @param msg 消息
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("收到消息为" + msg.toString());
        //非常耗时的操作
        Thread.sleep(5 * 1000);
        //普通异步任务
        ctx.channel().eventLoop().execute(() -> {
            ctx.writeAndFlush(Unpooled.copiedBuffer("hello 客户端", StandardCharsets.UTF_8));
        });
        //定时异步任务
        ctx.channel().eventLoop().schedule(() -> {
            ctx.writeAndFlush(Unpooled.copiedBuffer("hello 客户端1", StandardCharsets.UTF_8));
        }, 5, TimeUnit.MINUTES);
        System.out.println("发送完成------------------");
//        System.out.println("server ctx = " + ctx);
//        // 将msg 转换byteBuf
//        ByteBuf buf = (ByteBuf) msg;
//        System.out.println("收到 客户端 消息为:" + buf.toString(StandardCharsets.UTF_8));
//        System.out.println("地址 客户端 为:" + ctx.channel().remoteAddress());
    }

    /**
     * 数据读取完毕
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        // write 和 Flush
        ctx.writeAndFlush(Unpooled.copiedBuffer("i is server. hello clint", StandardCharsets.UTF_8));
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
