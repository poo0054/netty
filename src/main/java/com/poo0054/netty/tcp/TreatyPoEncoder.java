package com.poo0054.netty.tcp;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @author zhangzhi
 * @date 2023/4/6
 */
public class TreatyPoEncoder extends MessageToByteEncoder<TreatyPo> {

    @Override
    protected void encode(ChannelHandlerContext ctx, TreatyPo msg, ByteBuf out) throws Exception {
        System.out.println("准备开始编码!");
        out.writeInt(msg.getLen());
        out.writeBytes(msg.getData());
    }
}
