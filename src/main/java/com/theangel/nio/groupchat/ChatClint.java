package com.theangel.nio.groupchat;

import lombok.Getter;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Scanner;

/**
 * 客户端
 *
 * @author poo0054
 * @since 2022-06-19 12:46
 */
@Getter
public class ChatClint {
    public static void main(String[] args) {
        ChatClint clint = new ChatClint();
        clint.init();
        new Thread(() -> {
            while (true) {
                clint.read();
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        Scanner scanner = new Scanner(System.in);

        while (scanner.hasNextLine()) {
            String s = scanner.nextLine();
            clint.send(s);
        }
    }

    private static final String HOST = "127.0.0.1";
    public static final int PORT = 6667;
    private Selector selector;
    private SocketChannel socketChannel;
    private String name;

    /**
     * 初始化
     */
    private void init() {
        try {
            //创建多路选择器
            selector = Selector.open();
            //进行连接
            socketChannel = SocketChannel.open(new InetSocketAddress(HOST, PORT));
            //设置非阻塞
            socketChannel.configureBlocking(false);
            socketChannel.register(selector, SelectionKey.OP_READ);
            name = socketChannel.getLocalAddress().toString().substring(1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 发送消息
     *
     * @param msg
     */
    private void send(String msg) {
        try {
            socketChannel.write(ByteBuffer.wrap(msg.getBytes(StandardCharsets.UTF_8)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 读取服务器的消息
     */
    private void read() {
        try {
            int select = selector.select(2000);
            if (0 < select) {
                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                while (iterator.hasNext()) {
                    SelectionKey key = iterator.next();
                    if (key.isReadable()) {
                        SocketChannel readChannel = (SocketChannel) key.channel();
                        String s = null;
                        try {
                            s = ChatServer.getString(readChannel);
                        } catch (IOException e) {
                            key.cancel();
                            readChannel.close();
                            e.printStackTrace();
                            System.out.println(readChannel.getRemoteAddress() + "退出");

                        }
                        System.out.println(readChannel.getRemoteAddress() + "发送" + s);
                    }
                    iterator.remove();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
