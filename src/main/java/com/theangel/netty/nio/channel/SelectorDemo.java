package com.theangel.netty.nio.channel;

import org.junit.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * @author: poo0054
 * @create: 2022-06-12 14:49
 **/
public class SelectorDemo {
    @Test
    public void readTest() throws Exception {
        //打开服务器套接字通道
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        //绑定服务器
        serverSocketChannel.socket().bind(new InetSocketAddress(777));
        //开启异步
        serverSocketChannel.configureBlocking(false);
        //获取一个selector对象
        Selector selector = Selector.open();
        //绑定selector 事件为接收
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        //累积读取到所有的数
        String sum = "";
        while (true) {
            //等待一秒  获取事件
            if (selector.select(1000) == 0) {
                System.out.println("还没有ACCEPT事件");
                continue;
            }
            //此选择器的选定键集
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                //如果是连接
                if (key.isAcceptable()) {
                    SocketChannel socketChannel = serverSocketChannel.accept();
                    socketChannel.configureBlocking(false);
                    socketChannel.register(selector, SelectionKey.OP_READ, ByteBuffer.allocate(3));
                } else {
                    //如果是读取
                    if (key.isReadable()) {
                        SocketChannel channel = (SocketChannel) key.channel();
                        ByteBuffer byteBuffer = (ByteBuffer) key.attachment();
                        try {
                            int read = channel.read(byteBuffer);
                            if (read == -1) {
                                channel.close();
                            }
                            //反转 开始进行写操作
                            byteBuffer.flip();
                            while (byteBuffer.hasRemaining()) {
                                char b = (char) byteBuffer.get();
                                sum += b;
                                System.out.println(channel.hashCode() + "=== read：" + read + "====当前读取数：=====" + b + "======总数：====" + sum);
                            }
                            byteBuffer.clear();
                        } catch (IOException e) {
                            channel.close();
                            System.out.println("读取io异常:" + e.getMessage());
                        }
                    }


                }
                iterator.remove();
            }
        }
    }

    @Test
    public void witeTest() throws IOException {
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.configureBlocking(false);
        //连接服务器
        if (!socketChannel.connect(new InetSocketAddress("127.0.0.1", 777))) {
            //当且仅当此通道的套接字现在已连接时才为真
            while (!socketChannel.finishConnect()) {
                System.out.println("当前正在连接...");
            }
        }
        String s = "hello word";
        ByteBuffer wrap = ByteBuffer.wrap(s.getBytes());
        socketChannel.write(wrap);
        System.in.read();
    }


}
