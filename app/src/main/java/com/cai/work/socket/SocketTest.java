package com.cai.work.socket;

import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class SocketTest {

    public static void socketTest() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Socket socket = new Socket("47.75.37.208", 7709);
                    //向服务器发送消息
                    PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
                    String userID = "110";//presenter.getUserId();
                    userID = "hold|0|" + userID;
                    out.println(userID);
                    BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    String msg = br.readLine();
                    Log.d("socketTest", msg);
                    //关闭流
                    out.close();
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
