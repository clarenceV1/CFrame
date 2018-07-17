package com.meetone.work.share;

import android.util.Log;

import java.util.HashMap;

import cn.sharesdk.framework.PlatformActionListener;

public class ShareCallback implements PlatformActionListener {
    @Override
    public void onComplete(cn.sharesdk.framework.Platform platform, int i, HashMap<String, Object> hashMap) {
        Log.d("ShareCallback","platform:"+platform.getName());
    }

    @Override
    public void onError(cn.sharesdk.framework.Platform platform, int i, Throwable throwable) {
        Log.d("ShareCallback","platform:"+platform.getName());
    }

    @Override
    public void onCancel(cn.sharesdk.framework.Platform platform, int i) {
        Log.d("ShareCallback","platform:"+platform.getName());
    }
}
