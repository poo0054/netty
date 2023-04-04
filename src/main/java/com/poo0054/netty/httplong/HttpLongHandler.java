package com.poo0054.netty.httplong;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

/**
 * TextWebSocketFrame:表示一个文本帧
 *
 * @author zhangzhi
 * @date 2023/4/4
 */
public class HttpLongHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        System.out.println("服务器收到消息 : " + msg.text());
        TextWebSocketFrame textWebSocketFrame = new TextWebSocketFrame("hello i is server");
        ctx.writeAndFlush(textWebSocketFrame);
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        String longText = ctx.channel().id().asLongText();
        System.out.printf("客户端链接:%s id:%s  \n", ctx.channel().remoteAddress(), longText);
        TextWebSocketFrame textWebSocketFrame = new TextWebSocketFrame("hello i is server");
        ctx.writeAndFlush(textWebSocketFrame);
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        String longText = ctx.channel().id().asLongText();
        System.out.printf("客户端断开链接:%s id:%s  \n", ctx.channel().remoteAddress(), longText);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
