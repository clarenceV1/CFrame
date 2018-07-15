package com.cai.work.ui.main.fragment;

import android.annotation.SuppressLint;

import com.alibaba.fastjson.JSON;
import com.cai.framework.base.BaseLifecycleObserver;
import com.cai.framework.base.GodBasePresenter;
import com.cai.lib.logger.Logger;
import com.cai.work.bean.User;
import com.cai.work.bean.HomeDataSql;
import com.cai.work.bean.respond.HomeRespond;
import com.cai.work.bean.home.HomeItemData;
import com.cai.work.common.RequestStore;
import com.cai.work.dao.UserDAO;
import com.cai.work.dao.HomeDataSqlDAO;
import com.cai.work.event.ListViewScrollEvent;
import com.cai.work.ui.forward.ForwardActivity;
import com.example.clarence.utillibrary.StringUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

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
    UserDAO userDAO;
    Disposable interval;

    @Inject
    public MainHomePresenter() {
    }

    @SuppressLint("CheckResult")
    public void getAccountInfo() {
        Observable.create(new ObservableOnSubscribe<Map<String, User>>() {
            @Override
            public void subscribe(ObservableEmitter<Map<String, User>> observableEmitter) {
                Map<String, User> map = new HashMap<>();
                User user = userDAO.getData();
                if (user != null) {
                    user.setMobile(StringUtils.encryptMobile(user.getMobile()));
                    map.put("user", user);
                }
                observableEmitter.onNext(map);
            }
        }).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Map<String, User>>() {
                    @Override
                    public void accept(Map<String, User> map) {
                        mView.reFreshTopView(map.get("user"));
                    }
                });
    }

    public boolean isLogin() {
        User user = userDAO.getData();
        return user != null;
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
                    homeData.onNext(JSON.parseObject(homeDataSql.getData(), HomeItemData.class));
                    Logger.d("获取到首页缓存数据成功");
                } else {
                    Logger.d("获取到首页缓存数据失败");
                    homeData.onNext(null);
                }
            }
        }).subscribeOn(Schedulers.newThread())
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
                    public void accept(Throwable throwable) {
                        requestHomeData();
                    }
                });

    }

    private void requestHomeData() {
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

    public void startTimes() {
        if (interval != null) {
            interval.dispose();
            mCompositeSubscription.remove(interval);
        }
        interval = Observable.interval(0, 1, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        EventBus.getDefault().post(new ListViewScrollEvent());
                    }
                });
        mCompositeSubscription.add(interval);
    }

}
