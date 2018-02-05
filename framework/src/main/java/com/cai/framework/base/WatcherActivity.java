package com.cai.framework.base;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import com.cai.framework.watcher.ActivityStackWatcher;
import com.cai.framework.watcher.WatcherManager;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * Created by clarence on 2018/2/5.
 */

public  class WatcherActivity extends FragmentActivity {
    protected Object[] args = null;

    static {
        initWatcher();
    }

    private static void initWatcher() {
        WatcherManager.getInstance().addWatcher("activity-stack", new ActivityStackWatcher());
    }

    protected Object[] getArgs() {
        if (this.args == null) {
            WeakReference<Activity> weakReference = new WeakReference(this);
            this.args = new Object[]{weakReference, null};
        }
        return this.args;
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WatcherManager.getInstance().onCreate(this.getArgs());
    }

    public void finish() {
        super.finish();
        WatcherManager.getInstance().onFinish(this.getArgs());
    }

    protected void onStart() {
        super.onStart();
        WatcherManager.getInstance().onStart(this.getArgs());
    }

    protected void onResume() {
        super.onResume();
        WatcherManager.getInstance().onResume(this.getArgs());
    }

    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        this.getArgs()[1] = Boolean.valueOf(hasFocus);
        WatcherManager.getInstance().onWindowFocusChanged(this.getArgs());
    }

    protected void onPause() {
        super.onPause();
        WatcherManager.getInstance().onPause(this.getArgs());
    }

    protected void onStop() {
        super.onStop();
        WatcherManager.getInstance().onStop(this.getArgs());
    }

    protected void onDestroy() {
        super.onDestroy();
        WatcherManager.getInstance().onDestroy(this.getArgs());
    }

    protected void onRestart() {
        super.onRestart();
        WatcherManager.getInstance().onRestart(this.getArgs());
    }

    @SuppressLint("RestrictedApi")
    public List<Fragment> getFragmentList() {
        FragmentManager fragmentManager = this.getSupportFragmentManager();
        return fragmentManager.getFragments();
    }

    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }
}
