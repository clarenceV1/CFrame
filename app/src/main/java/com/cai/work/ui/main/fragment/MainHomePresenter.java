package com.cai.work.ui.main.fragment;

import android.annotation.SuppressLint;

import com.cai.framework.base.BaseLifecycleObserver;
import com.cai.framework.base.GodBasePresenter;
import com.cai.work.bean.User;
import com.cai.work.bean.home.HomeItemData;
import com.cai.work.bean.respond.HomeRespond;
import com.cai.work.common.RequestStore;
import com.cai.work.dao.HomeDataSqlDAO;
import com.cai.work.dao.UserDAO;
import com.cai.work.event.ListViewScrollEvent;
import com.example.clarence.utillibrary.StringUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

    public void requestHomeData() {
        try {
            Disposable disposable = requestStore.requestHomeData(new Consumer<HomeRespond>() {
                @Override
                public void accept(HomeRespond data) {
                    HomeItemData itemData = data.getData();
                    List<HomeItemData> list = new ArrayList<>();
                    if (itemData != null) {
                        list.add(itemData);
                        list.add(itemData);
                        list.add(itemData);
                        list.add(itemData);
                        list.add(itemData);
                    }
                    mView.reFreshView(list);
                }
            }, new Consumer<Throwable>() {
                @Override
                public void accept(Throwable throwable) {
                    mView.requestError("抱歉！请求失败，请检查您的网络");
                    mView.reFreshView(new ArrayList<HomeItemData>());
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
        interval = Observable.interval(0, 59, TimeUnit.MILLISECONDS)
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
