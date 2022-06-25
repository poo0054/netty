package com.theangel.nio.copy;

import org.junit.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author poo0054
 * @since 2022-06-19 16:32
 */
public class OldIOTest {

    @Test
    public void serverTest() throws IOException {
        ServerSocket serverSocket = new ServerSocket();
        serverSocket.bind(new InetSocketAddress(8989));
        Socket accept = serverSocket.accept();
        InputStream inputStream = accept.getInputStream();
        byte[] bytes = new byte[1024];
        String msg = "";
        while (true) {
            int read = inputStream.read(bytes, 0, bytes.length);
            msg += bytes.toString();
            if (-1 == read) {
                break;
            }
            System.out.println("接收到为" + msg);
        }
    }


    @Test
    public void clientTest() throws IOException {
        Socket socket = new Socket("localhost", 8989);
        String fileName = "D:\\迅雷下载/whql-amd-software-adrenalin-edition-22.5.2-win10-win11-may31.exe";
        long l = System.currentTimeMillis();
        //读取
        FileInputStream inputStream = new FileInputStream(fileName);
        //写入
        OutputStream outputStream = socket.getOutputStream();
        byte[] bytes = new byte[1024];
        int size = 0;
        int sizeleng = 0;
        while (size >= 0) {
            size = inputStream.read(bytes);
            outputStream.write(bytes);
            sizeleng += size;
        }
        System.out.println("大小为：" + sizeleng + "=============时间" + (System.currentTimeMillis() - l));
        inputStream.close();
        outputStream.close();
        socket.close();

    }
}
