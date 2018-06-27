package com.cai.work.common;

import com.cai.framework.utils.LanguageLocalUtil;
import com.example.clarence.netlibrary.INet;

import java.util.HashMap;
import java.util.Map;

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

    public Map getRequestHeader(Map<String, String> headerMap) {
        if (headerMap == null) {
            headerMap = new HashMap<>();
        }
        headerMap.put("version", "1.0");
        headerMap.put("platform", 2 + "");
        headerMap.put("openudid", "11111");
        headerMap.put("imei", "2222");
        headerMap.put("lang", "1");
        headerMap.put("auth", "3333");
        return headerMap;
    }

    /**
     * 获取更新数据
     *
     * @param onNext
     * @param onError
     * @return
     */
    public Disposable loadUpgrade(Consumer onNext, Consumer onError) {
        Disposable disposable = iNet.request().create(ApiService.class).loadUpgrade()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(onNext, onError);
        return disposable;
    }

    /**
     * 获取我对页面数据
     *
     * @param onNext
     * @param onError
     * @return
     */
    public Disposable loadMineData(Consumer onNext, Consumer onError) {
        Disposable disposable = iNet.request(getRequestHeader(null)).create(ApiService.class).loadMineData()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(onNext, onError);
        return disposable;
    }

}
