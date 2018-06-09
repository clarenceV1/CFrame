package com.cai.work.ui.main.fragment;

import android.annotation.SuppressLint;

import com.alibaba.fastjson.JSON;
import com.cai.framework.base.BaseLifecycleObserver;
import com.cai.framework.base.GodBasePresenter;
import com.cai.lib.logger.Logger;
import com.cai.work.bean.Account;
import com.cai.work.bean.HomeDataSql;
import com.cai.work.bean.home.HomeData;
import com.cai.work.bean.home.HomeItemData;
import com.cai.work.common.RequestStore;
import com.cai.work.dao.HomeDataSqlDAO;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class MainHomePresenter extends GodBasePresenter<HomeView> {
    @Inject
    RequestStore requestStore;
    @Inject
    HomeDataSqlDAO homeDataSqlDAO;

    @Inject
    public MainHomePresenter() {
    }

    public Account getAccountInfo() {
        Account account = new Account();
        account.setMoney("1000000.00");
        account.setName("1377****6287");
        account.setIcon("http://img5.imgtn.bdimg.com/it/u=269889177,603310778&fm=27&gp=0.jpg");
        return account;
    }

    @Override
    public void onAttached() {
        data.put(BaseLifecycleObserver.CLASS_NAME, "mainHomeFragment=====>");
    }

    @SuppressLint("CheckResult")
    public void requestData() {
      Observable.create(new ObservableOnSubscribe<HomeItemData>() {
            @Override
            public void subscribe(ObservableEmitter<HomeItemData> homeData) {
                HomeDataSql homeDataSql = homeDataSqlDAO.getHomeData();
                if (homeDataSql != null) {
                    homeData.onNext(JSON.parseObject(homeDataSql.getData(),HomeItemData.class));
                    Logger.d("获取到首页缓存数据成功");
                } else {
                    Logger.d("获取到首页缓存数据失败");
                    homeData.onNext(null);
                }
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<HomeItemData>() {
                    @Override
                    public void accept(HomeItemData homeData) {
                        if (homeData != null) {
                            mView.reFreshView(homeData);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable){
                        requestHomeData();
                    }
                });

    }

    private void requestHomeData() {
        try {
            Disposable disposable = requestStore.requestHomeData(new Consumer<HomeData>() {
                @Override
                public void accept(HomeData data) {
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
