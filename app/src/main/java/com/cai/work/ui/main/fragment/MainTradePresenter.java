package com.cai.work.ui.main.fragment;

import com.cai.framework.base.BaseLifecycleObserver;
import com.cai.framework.base.GodBasePresenter;
import com.cai.work.bean.respond.HomeRespond;
import com.cai.work.common.RequestStore;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class MainTradePresenter extends GodBasePresenter<HomeView> {
    @Inject
    RequestStore requestStore;

    @Inject
    public MainTradePresenter() {
    }


    @Override
    public void onAttached() {
        data.put(BaseLifecycleObserver.CLASS_NAME, "mainHomeFragment=====>");
    }


    public void requestTradeData() {
        try {
            Disposable disposable = requestStore.requestHomeData(new Consumer<HomeRespond>() {
                @Override
                public void accept(HomeRespond data) {
                    mView.reFreshView(data.getData());
                }
            }, new Consumer<Throwable>() {
                @Override
                public void accept(Throwable throwable) {
                    mView.requestError(throwable.getMessage());
                }
            });
            mCompositeSubscription.add(disposable);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
