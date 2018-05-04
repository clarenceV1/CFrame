package com.cai.work.base;

import android.content.Context;
import android.os.Build;
import android.os.StrictMode;

import com.alibaba.android.arouter.launcher.ARouter;
import com.cai.framework.base.GodBaseApplication;
import com.facebook.stetho.Stetho;
import com.github.moduth.blockcanary.BlockCanary;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

/**
 * Created by clarence on 2018/1/11.
 */

public class App extends GodBaseApplication {

    private RefWatcher refWatcher;

    public void onCreate() {
        super.onCreate();
        initRouter();

        initStetho();

        initBlockCanary(config.isDebug());

        initStrictMode(config.isDebug());

        initLeakCanary();
    }

    private void initRouter() {
        if (config.isDebug()) {           // 这两行必须写在init之前，否则这些配置在init过程中将无效
            ARouter.openLog();     // 打印日志
            ARouter.openDebug();   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
            ARouter.printStackTrace(); // 打印日志的时候打印线程堆栈
        }
        ARouter.init(getAppContext()); // 尽可能早，推荐在Application中初始化
    }

    private void initBlockCanary(boolean isTest) {
        if (isTest) {
            BlockCanary.install(GodBaseApplication.getAppContext(), new GodBlockCanaryContext()).start();
        }
    }

    private void initStrictMode(boolean isTest) {
        if (isTest && Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectAll().penaltyLog().build());
            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectAll().penaltyLog().build());
        }
    }

    private void initStetho() {
        if (!config.isUnitTest()) {
            Stetho.initializeWithDefaults(this);
        }
    }

    private void initLeakCanary() {
        if (!config.isUnitTest()) {
            refWatcher = setupLeakCanary();
        }
    }

    private RefWatcher setupLeakCanary() {
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return RefWatcher.DISABLED;
        }
        return LeakCanary.install(this);
    }

    public static RefWatcher getRefWatcher(Context context) {
        App application = (App) context.getApplicationContext();
        return application.refWatcher;
    }
}
