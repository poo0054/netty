package com.poo0054.netty.groupchat;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

/**
 * @author zhangzhi
 * @date 2023/4/4
 */
public class StateHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent stateEvent = (IdleStateEvent) evt;
            IdleState state = stateEvent.state();
            Channel channel = ctx.channel();
            System.out.println(channel.remoteAddress() + "发发生了" + state);
            switch (state) {
                case READER_IDLE:
                    //读空闲

                    break;
                case WRITER_IDLE:
                    //写空闲

                    break;
                case ALL_IDLE:
                    //读写空闲

                    break;
                default:
                    break;
            }
        }
    }
}
