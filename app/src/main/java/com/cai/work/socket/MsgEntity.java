package com.cai.work.socket;

import android.os.Handler;

public class MsgEntity {
    //要发送的消息
    private byte[] bytes;
    //错误处理的handler
    private Handler mHandler;

    public MsgEntity(byte[] bytes, Handler handler) {
        this.bytes = bytes;
        mHandler = handler;
    }

    public byte[] getBytes() {
        return this.bytes;
    }

    public Handler getHandler() {
        return mHandler;
    }
}
