package com.theangel.nio.copy;

import org.junit.Test;

import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * @author poo0054
 * @since 2022-06-19 16:32
 */
public class OldIOClint {


    @Test
    public void test() throws IOException {
        Socket socket = new Socket("localhost", 7001);
        String fileName = "";
        FileInputStream inputStream = new FileInputStream(fileName);

        DataOutputStream dataOutput = new DataOutputStream(socket.getOutputStream());
        byte[] bytes = new byte[1024];
        while (inputStream.read(bytes) >= 0) {
            dataOutput.write(bytes);
        }
        inputStream.close();
        dataOutput.close();
        socket.close();

    }
}
