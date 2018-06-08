package com.cai.work.ui.main.fragment;

import com.cai.framework.base.BaseLifecycleObserver;
import com.cai.framework.base.GodBasePresenter;
import com.cai.work.bean.Account;
import com.cai.work.bean.home.HomeData;
import com.cai.work.bean.IRecycleViewBaseData;
import com.cai.work.bean.home.HomeItemData;
import com.cai.work.common.RequestStore;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class MainHomePresenter extends GodBasePresenter<HomeView> {
    @Inject
    RequestStore requestStore;

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

    public void requestData() {
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
