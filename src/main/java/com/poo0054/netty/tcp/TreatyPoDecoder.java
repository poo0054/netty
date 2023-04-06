package com.poo0054.netty.tcp;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

import java.util.List;

/**
 * @author zhangzhi
 * @date 2023/4/6
 */
public class TreatyPoDecoder extends ReplayingDecoder<Void> {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        System.out.println("准备解码");
        TreatyPo treatyPo = new TreatyPo();
        treatyPo.setLen(in.readInt());
        byte[] bytes = new byte[treatyPo.getLen()];
        in.readBytes(bytes);
        treatyPo.setData(bytes);
        out.add(treatyPo);
    }
}
