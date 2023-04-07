package com.poo0054.netty.rpc.server;

import com.poo0054.netty.rpc.HelloServiceImpl;
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

    /**
     * 读取客户端发送的消息
     *
     * @param ctx 上下文对象 , 有管道(pipeline) 通道(channel) 链接信息
     * @param msg 消息
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("收到消息为" + msg.toString());
        //根据自定义规则调用指定的方法
        String hello = new HelloServiceImpl().hello(msg.toString());
        System.out.println("发送消息: " + hello);
        ctx.writeAndFlush(hello);
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
        cause.printStackTrace();
        ctx.close();
    }

}
