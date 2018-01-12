package com.cai.framework.base;

import android.app.Activity;
import android.app.Application;

import java.util.Stack;

/**
 * Created by clarence on 2018/1/11.
 */

public class BaseApplication extends Application {
    private Stack<Activity> store;
    private static BaseApplication baseApplication;

    public void onCreate() {
        super.onCreate();
        baseApplication = this;
        store = new Stack<>();
        registerActivityLifecycleCallbacks(new SwitchBackgroundCallbacks(store));
    }

    public static BaseApplication getAppContext() {
        return baseApplication;
    }

    /**
     * 获取当前的Activity
     *
     * @return
     */
    public Activity getCurActivity() {
        return store.lastElement();
    }
}
