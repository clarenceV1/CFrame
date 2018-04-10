package com.cai.work.common;

import com.cai.work.ApiService;
import com.example.clarence.netlibrary.INet;

import java.net.URLEncoder;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by clarence on 2018/3/26.
 */

public class RequestStore {
    @Inject
    INet iNet;

    @Inject
    public RequestStore() {
    }

    public Disposable requestWeather(String city, Consumer onNext, Consumer onError) throws Exception {
        String cityUtf = URLEncoder.encode(city, "utf-8");
        Disposable disposable = iNet.request().create(ApiService.class).getWeather(cityUtf)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(onNext, onError);
        return disposable;
    }
}
