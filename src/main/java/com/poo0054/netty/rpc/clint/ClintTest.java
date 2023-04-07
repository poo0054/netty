package com.poo0054.netty.rpc.clint;

import com.poo0054.netty.rpc.HelloService;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.lang.reflect.Proxy;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author zhangzhi
 * @date 2023/2/26
 */
public class ClintTest {
    ClintHandler clintHandler;
    ExecutorService executorService = Executors.newSingleThreadExecutor();

    public static void main(String[] args) throws InterruptedException {
        ClintTest clintTest = new ClintTest();
        HelloService proxy = (HelloService) clintTest.getProxy();
        String hello = proxy.hello("asdasd");
        System.out.println(hello);
    }

    private void start() throws InterruptedException {
        clintHandler = new ClintHandler();
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            // 设置客户端参数
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group) // 设置线程组
                    .channel(NioSocketChannel.class) // 设置客户端通道
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) {
                            // 加入自己的处理器
                            ch.pipeline().addLast(new StringEncoder());
                            ch.pipeline().addLast(new StringDecoder());
                            ch.pipeline().addLast(clintHandler);
                        }
                    });
            // 链接服务端
            ChannelFuture channelFuture = bootstrap.connect("127.0.0.1", 6669).sync();
            channelFuture.addListener(future -> {
                if (future.isSuccess()) {
                    System.out.println("启动成功");
                }
            });
            // 关闭
//            channelFuture.channel().closeFuture().sync();
        } finally {
            // 优雅关闭
//            group.shutdownGracefully();
        }
    }

    public Object getProxy() throws InterruptedException {
        if (null == clintHandler) {
            start();
        }
        return Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), new Class[]{HelloService.class}, (proxy, method, args) -> {
            clintHandler.setPar(args[0].toString());
            return executorService.submit(clintHandler).get();
        });
    }
}
