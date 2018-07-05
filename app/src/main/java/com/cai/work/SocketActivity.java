package com.cai.work;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import com.koushikdutta.async.ByteBufferList;
import com.koushikdutta.async.DataEmitter;
import com.koushikdutta.async.callback.DataCallback;
import com.koushikdutta.async.future.Future;
import com.koushikdutta.async.http.AsyncHttpClient;
import com.koushikdutta.async.http.WebSocket;

import java.util.concurrent.ExecutionException;

public class SocketActivity extends Activity {
    Future<WebSocket> future;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);

    }

    public void startSocket(View view) {
        future = AsyncHttpClient.getDefaultInstance().websocket(
                "ws://47.75.37.208:7709",// webSocket地址
                "7709",// 端口
                new AsyncHttpClient.WebSocketConnectCallback() {
                    @Override
                    public void onCompleted(Exception ex, WebSocket webSocket) {
                        if (ex != null) {
                            ex.printStackTrace();
                            return;
                        }
                        webSocket.setStringCallback(new WebSocket.StringCallback() {
                            public void onStringAvailable(String s) {
                                Log.i("SocketActivity", "onStringAvailable: " + s);
                            }
                        });
                        webSocket.setDataCallback(new DataCallback() {
                            public void onDataAvailable(DataEmitter emitter, ByteBufferList byteBufferList) {
                                Log.i("SocketActivity", "I got some bytes!");
                                // note that this data has been read
                                byteBufferList.recycle();
                            }
                        });
                    }
                });
    }

    public WebSocket getWebSocket() {
        try {
            return future.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void sendSocket(View view) {
        WebSocket webSocket = getWebSocket();
        webSocket.resume();
        if (webSocket != null) {
            webSocket.send("hold|0|684");
            webSocket.send(new byte[10]);
        }
    }

    public void pauseSocket(View view) {
        WebSocket webSocket = getWebSocket();
        if (webSocket != null) {
            if (webSocket.isPaused()) {
                webSocket.resume();
            } else {
                webSocket.pause();
            }
        }
    }

    public void stopSocket(View view) {
        WebSocket webSocket = getWebSocket();
        if (webSocket != null) {
            webSocket.close();
        }
    }
}
