package com.poo0054.netty.tcp;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

/**
 * 1.自定义一个handler 需要继承netty 规定好的handlerAdapter
 * <p>
 * 2. 自定义一个handler
 *
 * @author zhangzhi
 * @date 2023/2/26
 */
public class NettyClintHandler extends SimpleChannelInboundHandler<TreatyPo> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TreatyPo msg) throws Exception {
        System.out.println("客户端接收到消息长度为:" + msg.getLen() + ",数据为" + new String(msg.getData(), StandardCharsets.UTF_8));
        String string = UUID.randomUUID().toString();
        byte[] bytes = string.getBytes(StandardCharsets.UTF_8);
        TreatyPo treatyPo = new TreatyPo();
        treatyPo.setLen(bytes.length);
        treatyPo.setData(bytes);
        ctx.writeAndFlush(treatyPo);
    }

}
