package com.cai.work.ui.main.fragment;

import android.annotation.SuppressLint;

import com.alibaba.fastjson.JSON;
import com.cai.framework.base.BaseLifecycleObserver;
import com.cai.framework.base.GodBasePresenter;
import com.cai.lib.logger.Logger;
import com.cai.work.bean.HomeDataSql;
import com.cai.work.bean.User;
import com.cai.work.bean.home.HomeItemData;
import com.cai.work.bean.respond.HomeRespond;
import com.cai.work.bean.respond.ServiceRespond;
import com.cai.work.common.RequestStore;
import com.cai.work.dao.HomeDataSqlDAO;
import com.cai.work.dao.UserDAO;
import com.example.clarence.utillibrary.StringUtils;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class MainServicePresenter extends GodBasePresenter<MainServiceView> {
    @Inject
    RequestStore requestStore;

    @Inject
    public MainServicePresenter() {
    }

    @SuppressLint("CheckResult")
    public void requestData() {
        Disposable disposable = requestStore.requestAskData(new Consumer<ServiceRespond>() {
            @Override
            public void accept(ServiceRespond data) {
                if (data.getCode() == 200 && data.getData() != null && data.getData().size() > 0) {
                    mView.refreshData(data.getData());
                } else {
                    mView.toast(data.getResponseText());
                }

            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) {
                mView.toast("数据请求错误");
            }
        });
        mCompositeSubscription.add(disposable);
    }


    @Override
    public void onAttached() {

    }
}
