package com.cai.work.ui.welcome;

import com.alibaba.fastjson.JSON;
import com.cai.framework.base.GodBasePresenter;
import com.cai.work.base.App;
import com.cai.work.bean.respond.AppUpdateResond;
import com.cai.work.common.DataStore;
import com.cai.work.common.RequestStore;
import com.cai.work.dao.UserDAO;

import javax.inject.Inject;

import dagger.Lazy;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by clarence on 2018/1/12.
 */
public class WelcomePresenter extends GodBasePresenter<WelcomeView> {
    @Inject
    Lazy<DataStore> dataStore;
    @Inject
    Lazy<RequestStore> requestStore;
    @Inject
    Lazy<UserDAO> userDAO;

    @Inject
    public WelcomePresenter() {

    }

    @Override
    public void onAttached() {
        App.getAppComponent().inject(this);
    }

    public void loadUpgrade() {
        Disposable disposable = requestStore.get().loadUpgrade(new Consumer<AppUpdateResond>() {
            @Override
            public void accept(AppUpdateResond data) {
                dataStore.get().saveAppUpdate(JSON.toJSONString(data));
                mView.appUpdate();
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) {
                mView.appUpdate();
            }
        });
        mCompositeSubscription.add(disposable);
    }

    public void loadMineData() {
        Disposable disposable = requestStore.get().loadMineData(new Consumer<AppUpdateResond>() {
            @Override
            public void accept(AppUpdateResond data) {
                dataStore.get().saveAppUpdate(JSON.toJSONString(data));
                mView.appUpdate();
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) {
                mView.appUpdate();
            }
        });
        mCompositeSubscription.add(disposable);
    }
//    /**
//     * 请求首页数据
//     *
//     * @return
//     */
//    private void requestHomeData() {
//        Disposable disposable = requestStore.requestHomeData(new Consumer<HomeRespond>() {
//            @Override
//            public void accept(HomeRespond data) {
//
//            }
//        }, new Consumer<Throwable>() {
//            @Override
//            public void accept(Throwable throwable) {
//
//            }
//        });
//        mCompositeSubscription.add(disposable);
//    }
}
