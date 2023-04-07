package com.poo0054.netty.rpc.clint;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.concurrent.Callable;

/**
 * 1.自定义一个handler 需要继承netty 规定好的handlerAdapter
 * <p>
 * 2. 自定义一个handler
 *
 * @author zhangzhi
 * @date 2023/2/26
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ClintHandler extends ChannelInboundHandlerAdapter implements Callable<Object> {

    private ChannelHandlerContext ctx;

    private String result;

    private String par;

    /**
     * 通道就绪 触发事件
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        this.ctx = ctx;
    }

    /**
     * 收到消息
     *
     * @param ctx 上下文对象 , 有管道(pipeline) 通道(channel) 链接信息
     * @param msg 消息
     */
    @Override
    public synchronized void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        result = msg.toString();
        System.out.println("server ctx = " + ctx);
        notify();
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

    @Override
    public synchronized String call() throws Exception {
        ctx.writeAndFlush(par);
        //等待获取服务器的结果
        wait();
        return result;
    }
}
