package com.meetone.work.ui.welfare;

import com.example.clarence.netlibrary.NetRespondCallBack;
import com.meetone.work.base.AppBasePresenter;
import com.meetone.work.bean.Welfare;
import com.meetone.work.bean.respond.WelfareRespond;

import org.reactivestreams.Subscription;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class WelfarePresenter extends AppBasePresenter<WelfareView> {
    @Inject
    public WelfarePresenter() {
    }

    @Override
    public void onAttached() {

    }

    public void requestWelfare(boolean isloadCache) {
        if (isloadCache) {
            getWelfareOfCache();
        }
        getWelfareOfNet();
    }

    public void getWelfareOfCache() {
        Disposable disposable = Observable.create(new ObservableOnSubscribe<List<Welfare>>() {
            @Override
            public void subscribe(ObservableEmitter<List<Welfare>> e) {
                List<Welfare> welfareList = cacheStore.get().getWelfareList();
                if (welfareList == null) {
                    welfareList = new ArrayList<>();//rxjava 不允许传null
                }
                e.onNext(welfareList);
            }
        }).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Welfare>>() {
                    @Override
                    public void accept(List<Welfare> welfareList) {
                        if (welfareList != null && welfareList.size() > 0) {
                            mView.callBack(welfareList);
                        }
                    }
                });
        mCompositeSubscription.add(disposable);
    }

    public void getWelfareOfNet() {
        requestStore.get().requestWelfare()
                .doOnNext(new Consumer<WelfareRespond>() {
                    @Override
                    public void accept(WelfareRespond welfareRespond) {
                        if (welfareRespond.getErrorcode() == 0) {
                            List<Welfare> welfareList = welfareRespond.getData();
                            if (welfareList != null) {
                                cacheStore.get().saveWelfareList(welfareList);
                            }
                        }
                    }
                }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new NetRespondCallBack<WelfareRespond>() {
                    @Override
                    public void respondResult(Subscription subscription, WelfareRespond respond) {
                        if (respond.getErrorcode() == 0) {
                            mView.callBack(respond.getData());
                        } else {
                            mView.callBack(respond.getMessage());
                            mView.callBack(new ArrayList<Welfare>());
                        }
                    }

                    @Override
                    public void respondError(Throwable t) {
                        mView.callBack(t.getMessage());
                        mView.callBack(new ArrayList<Welfare>());
                    }
                });
    }
}