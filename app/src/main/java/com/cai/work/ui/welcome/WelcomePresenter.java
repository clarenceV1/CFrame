package com.cai.work.ui.welcome;

import com.alibaba.fastjson.JSON;
import com.cai.framework.base.GodBasePresenter;
import com.cai.work.bean.respond.AppUpdateResond;
import com.cai.work.common.DataStore;
import com.cai.work.common.RequestStore;
import com.cai.work.dagger.component.DaggerAppComponent;
import com.cai.work.dao.UserDAO;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by clarence on 2018/1/12.
 */
public class WelcomePresenter extends GodBasePresenter<WelcomeView> {
    @Inject
    DataStore dataStore;
    @Inject
    RequestStore requestStore;
    @Inject
    UserDAO userDAO;


    @Inject
    public WelcomePresenter() {

    }

    @Override
    public void onAttached() {
        DaggerAppComponent.create().inject(this);
    }

    public void loadUpgrade() {
        Disposable disposable = requestStore.loadUpgrade(new Consumer<AppUpdateResond>() {
            @Override
            public void accept(AppUpdateResond data) {
                dataStore.saveAppUpdate(JSON.toJSONString(data));
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
