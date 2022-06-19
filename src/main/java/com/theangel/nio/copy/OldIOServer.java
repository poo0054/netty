package com.theangel.nio.copy;

import org.junit.Test;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 原始的socket
 *
 * @author poo0054
 * @since 2022-06-19 16:30
 */
public class OldIOServer {

    @Test
    public void test() throws IOException {
        ServerSocket serverSocket = new ServerSocket(7001);
        while (true) {
            Socket socket = serverSocket.accept();
            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
            byte[] bytes = new byte[1024];
            while (true) {
                int read = dataInputStream.read(bytes, 0, bytes.length);
                if (-1 == read) {
                    break;
                }
            }
        }
    }
}
