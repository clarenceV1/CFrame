package com.meetone.work.ui.asset;

import com.cai.framework.http.NetRespondCallBack;
import com.meetone.work.base.AppBasePresenter;
import com.meetone.work.bean.Asset;
import com.meetone.work.bean.respond.AssetRespond;

import org.reactivestreams.Subscription;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class AssetPresenter extends AppBasePresenter<AssetView> {
    @Inject
    public AssetPresenter() {
    }

    @Override
    public void onAttached() {

    }

    public void requestAsset(boolean isloadCache, int page) {
        if (isloadCache) {
            getAssetfCache();
        }
        getAssetOfNet(page);
    }

    public void getAssetfCache() {
        Disposable disposable = Observable.create(new ObservableOnSubscribe<List<Asset>>() {
            @Override
            public void subscribe(ObservableEmitter<List<Asset>> e) {
                List<Asset> assetList = cacheStore.get().getAssetList();
                if (assetList == null) {
                    assetList = new ArrayList<>();//rxjava 不允许传null
                }
                e.onNext(assetList);
            }
        }).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Asset>>() {
                    @Override
                    public void accept(List<Asset> assetList) {
                        if (assetList != null && assetList.size() > 0) {
                            mView.callBack(assetList);
                        }
                    }
                });
        mCompositeSubscription.add(disposable);
    }

    public void getAssetOfNet(int page) {
        Map<String, String> params = new HashMap<>();
        params.put("page", page + "");
        requestStore.get().requestAsset(params)
                .doOnNext(new Consumer<AssetRespond>() {
                    @Override
                    public void accept(AssetRespond assetRespond) {
                        if (assetRespond.getErrorcode() == 0) {
                            List<Asset> assetList = assetRespond.getData();
                            if (assetList != null) {
                                cacheStore.get().saveAssetList(assetList);
                            }
                        }
                    }
                }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new NetRespondCallBack<AssetRespond>() {
                    @Override
                    public void respondResult(Subscription subscription, AssetRespond respond) {
                        if (respond.getErrorcode() == 0) {
                            mView.callBack(respond.getData());
                        } else {
                            mView.callBack(respond.getMessage());
                            mView.callBack(new ArrayList<Asset>());
                        }
                    }

                    @Override
                    public void respondError(Throwable t) {
                        mView.callBack(t.getMessage());
                        mView.callBack(new ArrayList<Asset>());
                    }
                });
    }
}
