package com.cai.work.base;

import com.cai.framework.base.GodBaseApplication;
import com.cai.work.bean.MyObjectBox;

import io.objectbox.BoxStore;

/**
 * Created by clarence on 2018/1/11.
 */

public class App extends GodBaseApplication {
    private static App application;

    public void onCreate() {
        super.onCreate();
        application = this;
    }

    public static App getAppContext() {
        return application;
    }
}
