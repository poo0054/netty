package com.theangel.netty.nio.channel;


import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author admin
 * Date: 2022/3/17 10:57
 */
public class SimpleWriteAndRed {
    static String str = "D:/file1.txt";

    public static void main(String[] args) throws IOException {
//        write();
        red();

    }

    private static void red() throws IOException {
        File file = new File(str);
        FileInputStream fileInputStream = new FileInputStream(str);
        FileChannel channel = fileInputStream.getChannel();
        //创建一个缓存区
        ByteBuffer byteBuffer = ByteBuffer.allocate((int) file.length());
        //读入buffer中
        channel.read(byteBuffer);
        //需要转换
        byteBuffer.flip();
        System.out.println(new String(byteBuffer.array()));

        fileInputStream.close();
    }

    private static void write() throws IOException {
        String str = "你好";
        //创建一个输出流
        FileOutputStream fileOutputStream = new FileOutputStream(str);

        //这个类型是FileChannelImpl   FileChannel是一个抽象类
        FileChannel channel = fileOutputStream.getChannel();
        //创建一个缓存区
        ByteBuffer byteBuffer = ByteBuffer.allocate(str.length());
        //把str放入buff中
        byteBuffer.put(str.getBytes());
        //反转
        byteBuffer.flip();
        //把数据写入到channel中
        channel.write(byteBuffer);

        fileOutputStream.close();
    }
}
