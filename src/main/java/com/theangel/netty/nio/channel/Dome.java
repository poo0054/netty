package com.theangel.netty.nio.channel;

import org.junit.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * @author: poo0054
 * @create: 2022-06-07 20:32
 */
public class Dome {
    @Test
    public void aTest() throws IOException {
        // 使用ServerSocketChannel 和  InetSocketAddress 网络
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        InetSocketAddress inetSocketAddress = new InetSocketAddress(7000);
        //绑定 并启动
        serverSocketChannel.socket().bind(inetSocketAddress);

        //创建buffer
        ByteBuffer[] byteBuffers = new ByteBuffer[2];
        byteBuffers[0] = ByteBuffer.allocate(5);
        byteBuffers[1] = ByteBuffer.allocate(3);

        //等待客户端连接
        SocketChannel accept = serverSocketChannel.accept();


    }
}
