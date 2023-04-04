package com.poo0054.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import org.junit.Test;

import java.nio.charset.StandardCharsets;

/**
 * @author zhangzhi
 * @date 2023/4/4
 */
public class ByteBufTest {
    @Test
    public void byteBufTest1() {
        //创建一个对象 该对象包含一个数组 byte[10]

        //底层维护了readerIndex和writeIndex俩个参数 读取下标和写入下标
        ByteBuf buffer = Unpooled.buffer(10);
        for (int i = 0; i < 10; i++) {
            buffer.writeByte(i);
        }

        //输出   不需要进行反转
        while (buffer.isReadable()) {
            System.out.println(buffer.readByte());
        }
    }

    @Test
    public void byteBufTest2() {
        ByteBuf byteBuf = Unpooled.copiedBuffer("hello,world!-", StandardCharsets.UTF_8);
        System.out.println(byteBuf);
        while (byteBuf.isReadable()) {
            System.out.println((char) byteBuf.readByte());
        }
        //截断读取 从第几个开始读  读取几个字符
        System.out.println(byteBuf.getCharSequence(0, 4, StandardCharsets.UTF_8));
    }
}
