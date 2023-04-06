package com.poo0054.netty.tcp;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.nio.charset.StandardCharsets;

/**
 * 1.自定义一个handler 需要继承netty 规定好的handlerAdapter
 * <p>
 * 2. 自定义一个handler
 *
 * @author zhangzhi
 * @date 2023/2/26
 */
public class NettyServerHandler extends SimpleChannelInboundHandler<TreatyPo> {

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) {
        System.out.println("channelActive 准备发送消息");
        for (int i = 0; i < 10; i++) {
            TreatyPo treatyPo = new TreatyPo();
            String s = "hello " + i;
            byte[] bytes = s.getBytes(StandardCharsets.UTF_8);
            treatyPo.setData(bytes);
            treatyPo.setLen(bytes.length);
            ctx.writeAndFlush(treatyPo);
        }
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TreatyPo msg) throws Exception {
        System.out.println("客户端接收到消息长度为:" + msg.getLen() + ",数据为" + new String(msg.getData(), StandardCharsets.UTF_8));
    }
}
