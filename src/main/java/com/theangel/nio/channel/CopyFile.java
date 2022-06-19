package com.theangel.nio.channel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 拷贝一个文件
 *
 * @author poo0054
 * @since : 2022/3/18 15:50
 */
public class CopyFile {
    public static void main(String[] args) throws IOException {
        //创建一个输入流
        FileInputStream fileInputStream = new FileInputStream(SimpleWriteAndRed.fileName);
        //创建输入流的通道
        FileChannel inputStreamChannel = fileInputStream.getChannel();
        //创建一个buffer
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(1024);
        //路径
        String s = CopyFile.class.getResource("/") + "1.txt";
//        D:\project\netty\file:\D:\project\netty\target\classes\com\theangel\netty\nio\channel\1.txt
        System.out.println(s);
        File file = new File(s);
        //创建文件
        if (!file.isFile()) {
            file.mkdirs();
            boolean newFile = file.createNewFile();
            System.out.println(newFile);
        }
        //创建一个输出流
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        //输出流通道
        FileChannel outputStreamChannel = fileOutputStream.getChannel();
        //循环读取
        while (true) {
            /**
             * 清空buffer
             *  position = 0;
             *         limit = capacity;
             *         mark = -1;
             *         return this;
             */
            byteBuffer.clear();
            //将input数据读取到buffer中
            int read = inputStreamChannel.read(byteBuffer);
            if (-1 == read) {
                //读取完毕了
                break;
            }
            //切换读
            byteBuffer.flip();
            //讲buffer数据 写入输出流中
            outputStreamChannel.write(byteBuffer);
        }
        fileInputStream.close();
        fileOutputStream.close();
    }
}
