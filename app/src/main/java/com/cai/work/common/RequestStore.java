package com.cai.work.common;

import android.content.Context;
import android.net.Uri;

import com.cai.framework.utils.LanguageLocalUtil;
import com.cai.work.base.App;
import com.cai.work.bean.respond.AppUpdateResond;
import com.example.clarence.netlibrary.INet;
import com.example.clarence.utillibrary.PackageUtils;
import com.example.clarence.utillibrary.UniqueIdUtils;
import com.example.clarence.utillibrary.encrypt.CipherUtil;

import org.reactivestreams.Subscription;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.Lazy;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by clarence on 2018/3/26.
 */
@Singleton
public class RequestStore {
    @Inject
    Lazy<INet> iNet;
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
        headerMap.put("openudid", UniqueIdUtils.getDeviceInfo(context, UniqueIdUtils.DEVICES_INFO.ANDROID_ID));
        headerMap.put("imei", UniqueIdUtils.getDeviceInfo(context, UniqueIdUtils.DEVICES_INFO.IMEI));
        headerMap.put("lang", LanguageLocalUtil.getSystemLanguage().toLowerCase());
        headerMap.put("auth", dataStore.get().getAuthorization());
        return headerMap;
    }

    public Map<String, String> getRequestHeader() {
        return getRequestHeader(null);
    }

    /**
     * 获取更新数据
     * @return
     */
    public Flowable<AppUpdateResond> loadUpgrade() {
        Flowable<AppUpdateResond> flowable = iNet.get().request().create(ApiService.class)
                .loadUpgrade(getRequestHeader())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
        return flowable;
    }

    /**
     * 获取我对页面数据
     *
     * @param onNext
     * @param onError
     * @return
     */
    public Disposable loadMineData(Consumer onNext, Consumer<Throwable> onError) {
        Disposable disposable = iNet.get().request().create(ApiService.class).loadMineData(getRequestHeader())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(onNext, onError);
        return disposable;
    }
}
