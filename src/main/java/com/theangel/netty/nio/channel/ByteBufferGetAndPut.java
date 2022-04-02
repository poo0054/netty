package com.theangel.netty.nio.channel;

import java.nio.ByteBuffer;

/**
 * @author: theangel
 * @create: 2022-03-20 19:49
 **/
public class ByteBufferGetAndPut {
    public static void main(String[] args) {
        ByteBuffer buffer = ByteBuffer.allocate(1024);

        //按照类型化方式放入数据
        buffer.putInt(100);
        buffer.putLong(123);
        buffer.putChar('1');

        //一定记得切换取出
        buffer.flip();

       /* System.out.println(buffer.getInt());
        System.out.println(buffer.getLong());
        System.out.println(buffer.getChar());*/

        //也可以用只读的buffer
        ByteBuffer buffer1 = buffer.asReadOnlyBuffer();
        while (buffer1.hasRemaining()) {
            System.out.println(buffer1.get());
        }


    }
}
