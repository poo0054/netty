package com.theangel.netty.nio.channel;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

/**
 * 快速拷贝图片
 */
public class CopyImage {
    public static void main(String[] args) throws IOException {


        FileInputStream fileInputStream = new FileInputStream("D:/a1.png");
        FileOutputStream fileOutputStream = new FileOutputStream("D:/a2.png");


        //获取流对应的通道

        FileChannel inputStreamChannel = fileInputStream.getChannel();

        FileChannel outputStreamChannel = fileOutputStream.getChannel();

        //使用输出流拷贝输入流
        outputStreamChannel.transferFrom(inputStreamChannel, 0, inputStreamChannel.size());

        inputStreamChannel.close();
        outputStreamChannel.close();
    }
}
