package com.cai.work.base;

import com.alibaba.android.arouter.launcher.ARouter;
import com.cai.framework.base.GodBaseApplication;
import com.cai.work.bean.MyObjectBox;
import com.cai.work.dagger.component.AppComponent;
import com.cai.work.dagger.component.DaggerAppComponent;

import io.objectbox.BoxStore;

/**
 * Created by clarence on 2018/1/11.
 */

public class App extends GodBaseApplication {

    public static boolean isDebug = true;
    public static String BASEURL = "https://more.ethte.com/api/";

    public static BoxStore boxStore;
    public static AppComponent appComponent;

    public void onCreate() {
        super.onCreate();
        initRouter();
        boxStore = MyObjectBox.builder().androidContext(this).build();
        if (appComponent == null) {
            appComponent = DaggerAppComponent.create();
        }

    }

    @Override
    public void initWebProtocol() {

    }

    public static BoxStore getBoxStore() {
        return boxStore;
    }

    @Override
    public String getBaseUrl() {
        return BASEURL;
    }

    @Override
    public boolean isDebug() {
        return isDebug;
    }

    private void initRouter() {
        if (isDebug) {           // 这两行必须写在init之前，否则这些配置在init过程中将无效
            ARouter.openLog();     // 打印日志
            ARouter.openDebug();   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
            ARouter.printStackTrace(); // 打印日志的时候打印线程堆栈
        }
        ARouter.init(getAppContext()); // 尽可能早，推荐在Application中初始化
    }

    public static AppComponent getAppComponent() {
        return appComponent;
    }
}
