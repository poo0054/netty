package com.theangel.netty.nio.channel;

import org.junit.Test;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * MappedByteBuffer文件直接可以在内存中修改  不需要再进行拷贝
 *
 * @author: theangel
 * @create: 2022-04-03 12:47
 **/
public class MappedByteBufferTest {
    @Test
    public void test() throws IOException {
        RandomAccessFile rw = new RandomAccessFile("1.txt", "rw");
        //获取通道
        FileChannel channel = rw.getChannel();
        /*
         * 参数1： 使用读写模式
         * 参数2： 0 起始位置  可以直接修改的起始位置
         * 参数3： 5 映射到内存的大写
         * 可以直接修改的范围0-5
         */
        MappedByteBuffer map = channel.map(FileChannel.MapMode.READ_WRITE, 0, 5);

        map.put(0, (byte) 'H');
        map.clear();
        channel.close();
        rw.close();
    }

}
