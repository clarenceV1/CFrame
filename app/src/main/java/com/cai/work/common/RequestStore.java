package com.cai.work.common;

import com.example.clarence.netlibrary.INet;

import javax.inject.Inject;

/**
 * Created by clarence on 2018/3/26.
 */

public class RequestStore {
    @Inject
    INet iNet;

    @Inject
    public RequestStore() {
    }

//    public Disposable requestHomeData(Consumer onNext, Consumer onError) {
//        Disposable disposable = iNet.request().create(ApiService.class).getHomeData()
//                .subscribeOn(Schedulers.newThread())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(onNext, onError);
//        return disposable;
//    }
//
}
