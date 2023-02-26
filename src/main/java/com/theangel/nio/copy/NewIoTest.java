package com.theangel.nio.copy;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

import org.junit.Test;

/**
 * @author poo0054
 * @since 2022-06-25 07:07
 */
public class NewIoTest {
    @Test
    public void serverTest() throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.socket().bind(new InetSocketAddress(3333));
        SocketChannel socketChannel = serverSocketChannel.accept();
        ByteBuffer allocate = ByteBuffer.allocate(1024);
        try {
            while (socketChannel.read(allocate) > 0) {
                allocate.flip();
                StringBuilder msg = new StringBuilder();
                while (allocate.hasRemaining()) {
                    byte b = allocate.get();
                    msg.append(b);
                }
                allocate.clear();
                System.out.println("接受到的数据为：" + msg);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void clientTest() throws IOException {
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.connect(new InetSocketAddress(3333));

        FileChannel fileChannel = new FileInputStream("D:\\迅雷下载/whql-amd-software-adrenalin-edition-22.5.2-win10-win11-may31.exe").getChannel();
        long l = System.currentTimeMillis();
        fileChannel.transferTo(0, fileChannel.size(), socketChannel);
        System.out.println("传输时间" + (System.currentTimeMillis() - l) + "===大小：" + fileChannel.size());
    }
}
