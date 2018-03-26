package com.cai.work.base;

import com.cai.framework.base.GodBaseApplication;
import com.cai.work.bean.MyObjectBox;

import io.objectbox.BoxStore;

/**
 * Created by clarence on 2018/1/11.
 */

public class App extends GodBaseApplication {
    private static BoxStore boxStore;

    public void onCreate() {
        super.onCreate();
        boxStore = MyObjectBox.builder().androidContext(App.this).build();
    }

    public static BoxStore getBoxStore() {
        return boxStore;
    }
}
