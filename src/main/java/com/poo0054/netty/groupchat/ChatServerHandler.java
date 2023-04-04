package com.poo0054.netty.groupchat;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.text.SimpleDateFormat;

/**
 * @author zhangzhi
 * @date 2023/4/4
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ChatServerHandler extends SimpleChannelInboundHandler<String> {
    /**
     * 定义一个 channel 组
     * GlobalEventExecutor 全局事件处理器
     */
    private static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        Channel channelled = ctx.channel();
        System.out.println(channelled.remoteAddress() + "发送消息: " + msg);
        for (Channel channel : channelGroup) {
            if (channel == channelled) {
                channel.writeAndFlush("发送成功");
            } else {
                channel.writeAndFlush(channelled.remoteAddress() + "发送消息: " + msg);
            }
        }
    }

    /**
     * 链接建立
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) {
        //将该客户加入聊天的信息推送在线客户
        Channel channel = ctx.channel();
        channelGroup.writeAndFlush("客户端: " + channel.remoteAddress() + " 加入了聊天");
        //加入 channel 组
        channelGroup.add(channel);
    }

    /**
     * 活动状态
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        System.out.println("当前: " + ctx.channel().remoteAddress() + "处于活动状态");
    }


    /**
     * 断开链接
     */
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) {
        Channel channel = ctx.channel();
        System.out.println("当前: " + channel.remoteAddress() + "离开了");
        channelGroup.writeAndFlush("当前: " + channel.remoteAddress() + "断开了");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
