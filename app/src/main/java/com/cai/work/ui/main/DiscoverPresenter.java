package com.cai.work.ui.main;

import com.cai.work.base.AppBasePresenter;
import com.cai.work.bean.CandyList;
import com.cai.work.bean.Discover;
import com.cai.work.bean.respond.CandyListRespond;
import com.cai.work.bean.respond.DiscoverRespond;
import com.example.clarence.netlibrary.NetRespondCallBack;

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

public class DiscoverPresenter extends AppBasePresenter<DiscoverView>  {
    @Inject
    public DiscoverPresenter() {
    }

    @Override
    public void onAttached() {

    }
    public void requestDiscoverList(boolean isloadCache) {
        if (isloadCache) {
            getDiscoverListOfCache();
        }
        getDiscoverListOfNet();
    }

    public void getDiscoverListOfCache() {
        Disposable disposable = Observable.create(new ObservableOnSubscribe<List<Discover>>() {
            @Override
            public void subscribe(ObservableEmitter<List<Discover>> e) {
                List<Discover> discoverList = cacheStore.get().getDiscoverList();
                if(discoverList==null){
                    discoverList = new ArrayList<>();//rxjava 不允许传null
                }
                e.onNext(discoverList);
            }
        }).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Discover>>() {
                    @Override
                    public void accept(List<Discover> discoverList) {
                        if (discoverList != null && discoverList.size() > 0) {
                            mView.callBack(discoverList);
                        }
                    }
                });
        mCompositeSubscription.add(disposable);
    }

    public void getDiscoverListOfNet() {
        requestStore.get().questDiscoverList()
                .doOnNext(new Consumer<DiscoverRespond>() {
                    @Override
                    public void accept(DiscoverRespond discoverRespond) {
                        if (discoverRespond.getErrorcode() == 0) {
                            List<Discover> discoverList = discoverRespond.getData();
                            if (discoverList != null) {
                                cacheStore.get().saveDiscoverList(discoverList);
                            }
                        }
                    }
                }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new NetRespondCallBack<DiscoverRespond>() {
                    @Override
                    public void respondResult(Subscription subscription, DiscoverRespond respond) {
                        if (respond.getErrorcode() == 0) {
                            mView.callBack(respond.getData());
                        } else {
                            mView.callBack(respond.getMessage());
                            mView.callBack(new ArrayList<Discover>());
                        }
                    }

                    @Override
                    public void respondError(Throwable t) {
                        mView.callBack(t.getMessage());
                        mView.callBack(new ArrayList<Discover>());
                    }
                });
    }
}
