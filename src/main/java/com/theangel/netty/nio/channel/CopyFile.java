package com.theangel.netty.nio.channel;

import java.io.*;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 拷贝一个文件
 *
 * @author admin
 * Date: 2022/3/18 15:50
 */
public class CopyFile {
    public static void main(String[] args) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(SimpleWriteAndRed.str);
        FileChannel fileChannel = fileInputStream.getChannel();
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(1024);
        String s = CopyFile.class.getResource("/") + "1.txt";
//        D:\project\netty\file:\D:\project\netty\target\classes\com\theangel\netty\nio\channel\1.txt
        System.out.println(s);
        File file = new File(s);
        if (!file.isFile()) {
            file.mkdirs();
            boolean newFile = file.createNewFile();
            System.out.println(newFile);
        }

        FileOutputStream fileOutputStream = new FileOutputStream(file);
        FileChannel channel = fileOutputStream.getChannel();
        while (true) {
            /**
             * 清空buffer
             *  position = 0;
             *         limit = capacity;
             *         mark = -1;
             *         return this;
             */
            byteBuffer.clear();
            int read = fileChannel.read(byteBuffer);
            if (-1 == read) {
                //读取完毕了
                break;
            }
            byteBuffer.flip();
            channel.write(byteBuffer);
        }
        fileInputStream.close();
        fileOutputStream.close();
    }
}
