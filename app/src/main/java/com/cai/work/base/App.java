package com.cai.work.base;

import com.cai.framework.base.GodBaseApplication;
import com.cai.work.bean.MyObjectBox;

import io.objectbox.BoxStore;

/**
 * Created by clarence on 2018/1/11.
 */

public class App extends GodBaseApplication {
    private static BoxStore boxStore;
    private static App application;

    public void onCreate() {
        super.onCreate();
        application = this;
        boxStore = MyObjectBox.builder().androidContext(application).build();
    }

    public static BoxStore getBoxStore() {
        return boxStore;
    }
    public static App getAppContext() {
        return application;
    }
}
