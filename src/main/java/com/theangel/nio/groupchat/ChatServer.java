package com.theangel.nio.groupchat;

import lombok.Data;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;

/**
 * 服务器端
 *
 * @author poo0054
 * @since 2022-06-19 11:50
 */
@Data
public class ChatServer {

    /**
     * 多路复用器
     */
    private Selector selector;

    /**
     * 监听
     */
    private ServerSocketChannel serverSocketChannel;

    /**
     * 端口
     */
    private static final int PORT = 6667;

    /**
     * 主启动
     *
     * @param args
     */
    public static void main(String[] args) {
        ChatServer groupChatServer = new ChatServer();
        groupChatServer.init();
        groupChatServer.listen();
    }

    /**
     * 初始化
     */
    private void init() {
        try {
            //创建一个选择器
            selector = Selector.open();
            //开启服务器通道
            serverSocketChannel = ServerSocketChannel.open();
            //设置非阻塞
            serverSocketChannel.configureBlocking(false);
            //绑定端口
            serverSocketChannel.bind(new InetSocketAddress(PORT));
            //注册到选择器中
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 服务端监听
     */
    public void listen() {
        while (true) {
            try {
                //获取事件
                int select = selector.select(2000);
                if (select > 0) {
                    //获取所有事件集合
                    Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                    while (iterator.hasNext()) {
                        SelectionKey next = iterator.next();
                        //事件为连接
                        if (next.isAcceptable()) {
                            //接收连接 创建通道
                            SocketChannel socketChannel = serverSocketChannel.accept();
                            //设置通道为非阻塞
                            socketChannel.configureBlocking(false);
                            //注册此通道
                            socketChannel.register(selector, SelectionKey.OP_READ);
                            System.out.println(socketChannel.getRemoteAddress() + "上线了");
                        } else if (next.isReadable()) {
                            //写入事件
                            read(next);
                        }
                        //防止重复处理
                        iterator.remove();
                    }
                } else {
                    System.out.println("当前没有可用事件");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 读
     *
     * @param key Selector注册的令牌
     */
    private void read(SelectionKey key) {
        SocketChannel readChannel = null;
        try {
            //获取事件的channel
            readChannel = (SocketChannel) key.channel();
            String sendMag = getString(readChannel);
            System.out.println(readChannel.getRemoteAddress() + "发送消息" + sendMag);
            //发送给别的客户端
            waitOtherChannel(sendMag, key);
        } catch (IOException e) {
            try {
                System.out.println(readChannel.getRemoteAddress() + "退出");
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            key.cancel();
            try {
                readChannel.close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            e.printStackTrace();
        }
    }

    /**
     * 获取通道的数据
     *
     * @param readChannel 需要操作的通道
     * @return 读取出来的值
     * @throws IOException
     */
    public static String getString(SocketChannel readChannel) throws IOException {
        //创建一个buffer接收
        ByteBuffer allocate = ByteBuffer.allocate(3);
        //用来存放所有数据
        StringBuilder sendMag = new StringBuilder();
        //读取到了数据
        try {
            while (0 < readChannel.read(allocate)) {
                //反转
                allocate.flip();
                //还有下一个
                while (allocate.hasRemaining()) {
                    char aChar = (char) allocate.get();
                    sendMag.append(aChar);
                }
                //读取完毕进行清理
                allocate.clear();
            }
        } catch (IOException e) {

            throw new IOException();
        }
        return sendMag.toString();
    }

    /**
     * 发送给别的客户端
     *
     * @param sendMag 需要发送的消息
     * @param self    当前发送消息的通道  排除自己
     */
    private void waitOtherChannel(String sendMag, SelectionKey self) {
        SocketChannel socketChannel = null;
        for (SelectionKey key : selector.keys()) {
            Channel channel = key.channel();
            if (channel instanceof SocketChannel && self != key) {
                socketChannel = (SocketChannel) channel;
                ByteBuffer wrap = ByteBuffer.wrap(sendMag.getBytes(StandardCharsets.UTF_8));
                //写入通道
                try {
                    socketChannel.write(wrap);
                } catch (IOException e) {
                    e.printStackTrace();
                    try {
                        e.printStackTrace();
                        System.out.println("channel出错了" + socketChannel.getRemoteAddress());
                        key.cancel();
                        socketChannel.close();
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                }
            }
        }


    }


}
