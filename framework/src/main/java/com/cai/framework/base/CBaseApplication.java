package com.cai.framework.base;

import android.app.Activity;
import android.app.Application;

import com.cai.framework.store.StoreFactory;
import com.cai.framework.store.cache.MeetyouCacheLoader;

import java.util.Stack;

/**
 * Created by clarence on 2018/1/11.
 */

public class CBaseApplication extends Application {
    private Stack<Activity> store;
    private static CBaseApplication baseApplication;

    public void onCreate() {
        super.onCreate();
        baseApplication = this;
        StoreFactory.init(getAppContext());
        MeetyouCacheLoader.init(getAppContext());
        store = new Stack<>();
        registerActivityLifecycleCallbacks(new SwitchBackgroundCallbacks(store));
        BaseConfig.getInsatance().setSwitchMap(BaseConfig.IS_DEBUG, true);
    }

    public static CBaseApplication getAppContext() {
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
