package com.cai.work.base;

import android.os.Build;

import com.alibaba.android.arouter.launcher.ARouter;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cai.framework.base.GodBaseApplication;
import com.cai.framework.event.WebViewEvent;
import com.cai.framework.utils.LanguageLocalUtil;
import com.cai.framework.web.IWebProtocol;
import com.cai.framework.web.IWebProtocolCallback;
import com.cai.framework.web.WebProtocolDO;
import com.cai.framework.web.WebProtocolManager;
import com.cai.work.BuildConfig;
import com.cai.work.bean.MyObjectBox;
import com.cai.work.common.Constant;
import com.cai.work.dagger.component.AppComponent;
import com.cai.work.dagger.component.DaggerAppComponent;
import com.cai.work.share.ShareQQManager;
import com.cai.work.share.ShareSinaManager;
import com.cai.work.share.ShareWechatManager;
import com.cai.work.share.ShareWechatMomentsManager;
import com.example.clarence.utillibrary.ToastUtils;
import com.mob.MobSDK;
import com.tencent.bugly.crashreport.CrashReport;
import com.umeng.analytics.MobclickAgent;
import com.umeng.commonsdk.UMConfigure;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import io.objectbox.BoxStore;

/**
 * Created by clarence on 2018/1/11.
 */

public class App extends GodBaseApplication {
    public static BoxStore boxStore;
    public static AppComponent appComponent;

    @Inject
    AppPresenter appPresenter;

    public void onCreate() {
        super.onCreate();
        if (appComponent == null) {
            appComponent = DaggerAppComponent.create();
            appComponent.inject(this);
        }
        initRouter();
        boxStore = MyObjectBox.builder().androidContext(this).build();
        appPresenter.laodConfiguration();
        MobSDK.init(this);
        CrashReport.initCrashReport(getApplicationContext());
        UMConfigure.init(this, "5b20a535a40fa3053200023a", "", UMConfigure.DEVICE_TYPE_PHONE, "");
        MobclickAgent.setScenarioType(this, MobclickAgent.EScenarioType.E_UM_NORMAL);
    }

    @Override
    public void initWebProtocol() {
        WebProtocolManager.getInstall().init(new IWebProtocol() {
            @Override
            public void handlerProtocol(final WebProtocolDO protocolDO, final IWebProtocolCallback callback) {
                if (ProtocolConstant.SHARE_WECHAT.equals(protocolDO.getHost())) {
                    ShareWechatManager.shareWechatForProtocol(getAppContext(), protocolDO, callback);
                } else if (ProtocolConstant.SHARE_WECHAT_MOMENTS.equals(protocolDO.getHost())) {
                    ShareWechatMomentsManager.shareWechatMonentsForProtocol(getAppContext(), protocolDO, callback);
                } else if (ProtocolConstant.SHARE_QQ.equals(protocolDO.getHost())) {
                    ShareQQManager.shareQQForProtocol(getAppContext(), protocolDO, callback);
                } else if (ProtocolConstant.SHARE_SINA.equals(protocolDO.getHost())) {
                    ShareSinaManager.shareSinaForProtocol(getAppContext(), protocolDO, callback);
                } else if (ProtocolConstant.JUMP_COIN_DETAIL.equals(protocolDO.getHost())) {
                    String parama = protocolDO.getParams();
                    JSONObject jsonObject = JSON.parseObject(parama);
                    int id = jsonObject.getInteger("id");
                    ARouter.getInstance().build("/MeetOne/CandyDetailActivity")
                            .withInt("tokenId", id)
                            .navigation();
                } else if (ProtocolConstant.JUMP_LOGIN.equals(protocolDO.getHost())) {
                    ARouter.getInstance().build("/MeetOne/LoginActivity").navigation();
                } else if (ProtocolConstant.JUMP_APP_INFO.equals(protocolDO.getHost())) {
                    Map<String, String> map = new HashMap<>();
                    map.put("auth", appPresenter.getToken());
                    map.put("lang", LanguageLocalUtil.getSystemLanguage().toUpperCase());
                    callback.callBack(getAppContext(), protocolDO, StateCode.STATE_CODE_SUCCESS, "", JSON.toJSONString(map));
                } else if (ProtocolConstant.HIDE_WEB_CLOSE_BTN.equals(protocolDO.getHost())) {
                    EventBus.getDefault().post(new WebViewEvent(WebViewEvent.TYPE_HIDE_BTN, true));
                } else {
                    ToastUtils.showShort("无法识别该协议" + protocolDO.getHost());
                }
            }

            @Override
            public void jumpNewActivity(String url) {
                ARouter.getInstance().build("/MeetOne/WebActivity").withCharSequence("url", url).navigation();
            }
        });
    }

    public static BoxStore getBoxStore() {
        return boxStore;
    }

    @Override
    public String getBaseUrl() {
        if (BuildConfig.DEBUG) {
            if (Constant.isTestEnv()) {
                return Constant.TEST_BASE_URL;
            }
        }
        return Constant.OFFICIAL_BASE_URL;
    }

    @Override
    public boolean isDebug() {
        return Constant.IS_DEBUG;
    }

    private void initRouter() {
        if (Constant.IS_DEBUG) {           // 这两行必须写在init之前，否则这些配置在init过程中将无效
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
