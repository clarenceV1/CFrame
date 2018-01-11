package com.cai.work.base;

import android.app.Activity;
import android.app.Application;
import com.cai.annotation.aspect.CostTime;
import java.util.Stack;

/**
 * Created by clarence on 2018/1/11.
 */

public class App extends Application {
    private static App mApp;
    public Stack<Activity> store;

    @CostTime
    public void onCreate() {
        super.onCreate();
        mApp = this;
        store = new Stack<>();
        registerActivityLifecycleCallbacks(new SwitchBackgroundCallbacks(store));
    }

    public static App getAppContext() {
        return mApp;
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
