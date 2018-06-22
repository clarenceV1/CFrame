package com.cai.work.base;

import com.alibaba.android.arouter.launcher.ARouter;
import com.cai.framework.base.GodBaseApplication;
import com.cai.framework.web.IWebProtocol;
import com.cai.framework.web.WebProtocolManager;
import com.cai.work.bean.MyObjectBox;

import java.util.Map;

import io.objectbox.BoxStore;

/**
 * Created by clarence on 2018/1/11.
 */

public class App extends GodBaseApplication {

    public static boolean isDebug = true;
    public static String BASEURL = "http://www.hellceshi.com";

    public static BoxStore boxStore;

    public void onCreate() {
        super.onCreate();
        initRouter();
        boxStore = MyObjectBox.builder().androidContext(this).build();
        initWebProtocol();
    }

    private void initWebProtocol() {
        WebProtocolManager.getInstall().init(new IWebProtocol() {
            @Override
            public Map<String, String> handlerProtocol(String protocol, String params) {
                return null;
            }
        });
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
}
