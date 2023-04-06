package com.poo0054.netty.handle;

import com.poo0054.netty.codec.CodecPo;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.StandardCharsets;

/**
 * 1.自定义一个handler 需要继承netty 规定好的handlerAdapter
 * <p>
 * 2. 自定义一个handler
 *
 * @author zhangzhi
 * @date 2023/2/26
 */
public class ClintHandler extends ChannelInboundHandlerAdapter {
    /**
     * 通道就绪 触发事件
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) {

        System.out.println("客户端就绪");
        //发送CodecPo到服务器
        CodecPo.Study build = CodecPo.Study.newBuilder().setId(1).addEmail("123@outlook.com").setName("张三").build();
        System.out.println("ctx : " + ctx);
        ctx.writeAndFlush(build);
        ctx.writeAndFlush(123456L);
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


}
