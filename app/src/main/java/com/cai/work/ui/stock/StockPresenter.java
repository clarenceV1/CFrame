package com.cai.work.ui.stock;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;

import com.bumptech.glide.Glide;
import com.cai.framework.base.GodBasePresenter;
import com.cai.work.bean.respond.StockHQRespond;
import com.cai.work.bean.respond.StockListRespond;
import com.cai.work.bean.respond.StockTradeRespond;
import com.cai.work.common.RequestStore;
import com.cai.work.dao.AccountDAO;


import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class StockPresenter extends GodBasePresenter<StockView> {

    @Inject
    RequestStore requestStore;
    @Inject
    AccountDAO accountDAO;

    @Inject
    public StockPresenter() {
    }

    @Override
    public void onAttached() {

    }

    public void loadImage(final String imgeUrl) {
        Disposable disposable = Observable.create(new ObservableOnSubscribe<Bitmap>() {
            @Override
            public void subscribe(ObservableEmitter<Bitmap> e) throws Exception {
                Bitmap myBitmap = Glide.with(context)
                        .load(imgeUrl)
                        .asBitmap() //必须
                        .centerCrop()
                        .into(550, 450)
                        .get();
                e.onNext(myBitmap);
            }
        }).subscribeOn(Schedulers.newThread())
          .observeOn(AndroidSchedulers.mainThread())
           .subscribe(new Consumer<Bitmap>() {
                    @Override
                    public void accept(Bitmap bitmap) throws Exception {
                        if (bitmap != null) {
                            mView.showImage(bitmap);
                        }
                    }
                });
        mCompositeSubscription.add(disposable);
    }

    public void requestStockTrade() {
        String token = accountDAO.getToken();
        Disposable disposable = requestStore.requestStockTrade(token, new Consumer<StockTradeRespond>() {
            @Override
            public void accept(StockTradeRespond data) {
                mView.callBack(data.getData());
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) {
                mView.toast("请求异常", 3);
            }
        });
        mCompositeSubscription.add(disposable);
    }


    public void requestStockHq(String code) {
        Disposable disposable = requestStore.requestStockHq(code, new Consumer<StockHQRespond>() {
            @Override
            public void accept(StockHQRespond data) {
                mView.callBack(data.getData());
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) {
                mView.toast("请求异常", 3);
            }
        });
        mCompositeSubscription.add(disposable);
    }

    public void requestStockList(String search) {
        Disposable disposable = requestStore.requestStockList(search, new Consumer<StockListRespond>() {
            @Override
            public void accept(StockListRespond data) {
                mView.callBack(data.getData());
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) {
                mView.toast("请求异常", 3);
            }
        });
        mCompositeSubscription.add(disposable);
    }

    public void requestStockHistory(String code) {
        Disposable disposable = requestStore.requestStockHistory(code, new Consumer<String[][]>() {
            @Override
            public void accept(String[][] data) {
                mView.callBack(data);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) {
                mView.toast("请求异常", 3);
            }
        });
        mCompositeSubscription.add(disposable);
    }

}
