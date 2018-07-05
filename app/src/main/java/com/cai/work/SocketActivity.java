package com.cai.work;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.cai.work.socket.SocketManager;

public class SocketActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);
    }

    public void startSocket(View view) {
        SocketManager.connect();
    }

    public void sendSocket(View view) {
        SocketManager.sendSocket("hold|0|684");
    }

    public void pauseSocket(View view) {
        if (SocketManager.getWebSocket().isPaused()) {
            SocketManager.resumeSocket();
        } else {
            SocketManager.pauseSocket();
        }
    }

    public void stopSocket(View view) {
        SocketManager.closeSocket();
    }

}
