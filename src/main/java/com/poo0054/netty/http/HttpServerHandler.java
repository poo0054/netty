package com.poo0054.netty.http;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;

/**
 * HttpObject:客户端和服务器端相互通讯的数据封装成HttpObject
 *
 * @author zhangzhi
 * @date 2023/4/3
 */
public class HttpServerHandler extends SimpleChannelInboundHandler<HttpObject> {

    //读取客户端数据
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws URISyntaxException {
        //接收
        if (msg instanceof HttpRequest) {
            System.out.println("客户端地址:" + ctx.channel().remoteAddress());

            HttpRequest httpRequest = (HttpRequest) msg;
            //获取uri
            URI uri = new URI(httpRequest.uri());
            if ("/favicon.ico".equals(uri.getPath())) {
                System.out.println("不处理ico");
                return;
            }

            //回复 http协议
            ByteBuf byteBuf = Unpooled.copiedBuffer("hello,我是服务器", StandardCharsets.UTF_8);

            //构造http相应
            FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, byteBuf);

            //构建返回头
            response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain");
            response.headers().set(HttpHeaderNames.CONTENT_LENGTH, byteBuf.readableBytes());

            //发送
            ctx.writeAndFlush(response);
        }

    }
}
