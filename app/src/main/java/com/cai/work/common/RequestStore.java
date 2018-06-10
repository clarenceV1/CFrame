package com.cai.work.common;

import com.cai.work.ApiService;
import com.example.clarence.netlibrary.INet;

import java.net.URLEncoder;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.http.Query;

/**
 * Created by clarence on 2018/3/26.
 */

public class RequestStore {
    @Inject
    INet iNet;

    @Inject
    public RequestStore() {
    }

    public Disposable requestHomeData(Consumer onNext, Consumer onError) {
        Disposable disposable = iNet.request().create(ApiService.class).getHomeData()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(onNext, onError);
        return disposable;
    }

    public Disposable requestLogin(String username, String password, Consumer onNext, Consumer onError) {
        Disposable disposable = iNet.request().create(ApiService.class).requestLogin(username, password)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(onNext, onError);
        return disposable;
    }

    public Disposable requestUserInfo(String token, Consumer onNext, Consumer onError) {
        Disposable disposable = iNet.request().create(ApiService.class).getUserInfo(token)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(onNext, onError);
        return disposable;
    }

    public Disposable requestFundDetail(int page, String token, Consumer onNext, Consumer onError) {
        Disposable disposable = iNet.request().create(ApiService.class).requestFundDetail(page, token)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(onNext, onError);
        return disposable;
    }

    public Disposable requestBankList(String token, Consumer onNext, Consumer onError) {
        Disposable disposable = iNet.request().create(ApiService.class).requestBankList(token)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(onNext, onError);
        return disposable;
    }
}
