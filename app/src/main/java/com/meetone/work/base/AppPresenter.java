package com.meetone.work.base;

import com.example.clarence.netlibrary.NetRespondCallBack;
import com.meetone.work.bean.Qiniu;
import com.meetone.work.bean.respond.ConfigerRespond;
import com.meetone.work.common.DataCacheStore;
import com.meetone.work.common.DataStore;
import com.meetone.work.common.FileStore;
import com.meetone.work.common.RequestStore;
import com.meetone.work.dao.UserDAO;
import com.meetone.work.event.LoginEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.reactivestreams.Subscription;

import javax.inject.Inject;

import dagger.Lazy;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class AppPresenter {
    @Inject
    public Lazy<DataStore> dataStore;
    @Inject
    public Lazy<RequestStore> requestStore;
    @Inject
    public Lazy<FileStore> fileStore;
    @Inject
    public Lazy<UserDAO> userDAO;
    @Inject
    public Lazy<DataCacheStore> cacheStore;

    private static final int ConfigurationMax = 5;//配置請求，最多請求次数
    private int nowConfigurationCount = 0;

    @Inject
    public AppPresenter() {
        EventBus.getDefault().register(this);
    }

    public void laodConfiguration() {
        if (userDAO.get().isLogin() && nowConfigurationCount <= ConfigurationMax) {
            nowConfigurationCount += nowConfigurationCount;
            requestStore.get().laodConfiguration()
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new NetRespondCallBack<ConfigerRespond>() {
                        @Override
                        public void respondResult(Subscription subscription, ConfigerRespond respond) {
                            if (respond.getErrorcode() == 0 && respond.getData() != null) {
                                Qiniu qiniu = respond.getData().getQiniu();
                                if (qiniu != null && qiniu.getToken() != null) {
                                    dataStore.get().saveQiniuToken(qiniu.getToken());
                                }
                            }
                        }

                        @Override
                        public void respondError(Throwable t) {
                            t.getMessage();
                        }
                    });
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLoginEvent(LoginEvent event) {
        if (event.loginState == LoginEvent.STATE_LOGIN_IN) {
            laodConfiguration();
        }
    }

    public String getToken() {
        return userDAO.get().getToken();
    }
}
