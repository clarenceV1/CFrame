package com.cai.work.common;

import android.content.Context;

import com.cai.framework.utils.LanguageLocalUtil;
import com.cai.work.base.App;
import com.cai.work.bean.respond.AppUpdateResond;
import com.cai.work.bean.respond.CandyListRespond;
import com.cai.work.bean.respond.MineRespond;
import com.example.clarence.utillibrary.PackageUtils;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.Lazy;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

/**
 * Created by clarence on 2018/3/26.
 */
@Singleton
public class RequestStore {
    @Inject
    Lazy<Retrofit> retrofit;
    @Inject
    Lazy<DataStore> dataStore;

    @Inject
    public RequestStore() {
    }

    public Map<String, String> getRequestHeader(Map<String, String> headerMap) {
        if (headerMap == null) {
            headerMap = new HashMap<>();
        }
        Context context = App.getAppContext();
        headerMap.put("version", PackageUtils.getVersionName(context));
        headerMap.put("platform", "2");
//        headerMap.put("openudid", UniqueIdUtils.getDeviceInfo(context, UniqueIdUtils.DEVICES_INFO.ANDROID_ID));
//        headerMap.put("imei", UniqueIdUtils.getDeviceInfo(context, UniqueIdUtils.DEVICES_INFO.IMEI));
        headerMap.put("lang", LanguageLocalUtil.getSystemLanguage().toLowerCase());
        headerMap.put("auth", dataStore.get().getAuthorization());
        return headerMap;
    }

    public Map<String, String> getRequestHeader() {
        return getRequestHeader(null);
    }

    /**
     * 获取更新数据
     *
     * @return
     */
    public Flowable<AppUpdateResond> loadUpgrade() {
        Flowable<AppUpdateResond> flowable = retrofit.get().create(ApiService.class)
                .loadUpgrade(getRequestHeader())
                .subscribeOn(Schedulers.newThread());
        return flowable;
    }

    /**
     * 获取我对页面数据
     *
     * @return
     */
    public Flowable<MineRespond> loadMineData() {
        Flowable<MineRespond> flowable = retrofit.get().create(ApiService.class)
                .loadMineData(getRequestHeader())
                .subscribeOn(Schedulers.newThread());
        return flowable;
    }

    /**
     * 获取糖果列表
     *
     * @return
     */
    public Flowable<CandyListRespond> questCandyList() {
        Flowable<CandyListRespond> flowable = retrofit.get().create(ApiService.class)
                .questCandyList(getRequestHeader())
                .subscribeOn(Schedulers.newThread());
        return flowable;
    }

    /**
     * 获取糖果列表
     *
     * @return
     */
    public Flowable<CandyListRespond> receiveCandy(int tokenId) {
        Flowable<CandyListRespond> flowable = retrofit.get().create(ApiService.class)
                .receiveCandy(getRequestHeader(), tokenId)
                .subscribeOn(Schedulers.newThread());
        return flowable;
    }
}
