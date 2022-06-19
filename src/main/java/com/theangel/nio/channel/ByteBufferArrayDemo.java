package com.theangel.nio.channel;

import org.junit.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Arrays;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * buffer数组操作
 * 该方法 只是用来测试buffer数组的操作 未完善
 *
 * @author poo0054
 * @since 2022-06-07 20:32
 */
public class ByteBufferArrayDemo {
    @Test
    public void bufferArrayTest() throws IOException {
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
        SocketChannel socketChannel = serverSocketChannel.accept();
        //最大读取的值
        int messageLength = 8;
        //循环读取
        while (true) {
            //本次读取到的长度
            int byteRead = 0;
            //读取的字节数，可能为零，如果通道已到达流尾，则为-1
            while (messageLength > byteRead) {
                //本次从客户端接收到的长度
                final long l = socketChannel.read(byteBuffers);
                byteRead += l;
                //累积读取的字节数
                System.out.printf("累积读取的个数%s\n", messageLength);
                //使用流打印
                Arrays.stream(byteBuffers)
                        .map(item -> item.limit() + ":limit\t" + item.position() + ":position")
                        .forEach(System.out::println);
            }
            //反转后才能读取
            Arrays.asList(byteBuffers).forEach(Buffer::flip);

            //当前读取的长度
            int byteWire = 0;
            while (messageLength > byteWire) {
                //显示回客户端
                long l = socketChannel.write(byteBuffers);
                byteWire += l;
            }

            //使用完之后  需要清理
            Arrays.asList(byteBuffers).forEach(Buffer::clear);

            System.out.println("byteRead" + byteRead + "\n byteWire" + byteWire);
        }
    }

    /**
     * 测试多线程情况下的local
     * 测试多线程的重复使用
     */
    @Test
    public void test() throws ExecutionException, InterruptedException {
        ThreadLocal<String> threadLocal = new InheritableThreadLocal<>();
        String s = "123";
        threadLocal.set(s);
        ExecutorService executorService = Executors.newFixedThreadPool(2);

        executorService.submit(() -> {
            System.out.println("1=============" + Thread.currentThread().getId() + "====" + Thread.currentThread().getName() + "=====" + threadLocal.get());
            threadLocal.remove();
        });

        Future<?> submit = executorService.submit(() -> {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("2=============" + Thread.currentThread().getId() + "====" + Thread.currentThread().getName() + "=====" + threadLocal.get());
        });


        executorService.submit(() -> System.out.println("3=============" + Thread.currentThread().getId() + "====" + Thread.currentThread().getName() + "=====" + threadLocal.get()));

        System.out.println("main=============" + threadLocal.get());
        submit.get();
    }
}
