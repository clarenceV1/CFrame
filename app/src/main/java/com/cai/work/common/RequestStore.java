package com.cai.work.common;

import android.content.Context;
import android.text.TextUtils;

import com.alibaba.fastjson.JSONObject;
import com.cai.framework.utils.LanguageLocalUtil;
import com.cai.work.base.App;
import com.cai.work.bean.respond.AppUpdateResond;
import com.cai.work.bean.respond.AssetRespond;
import com.cai.work.bean.respond.CandyListRespond;
import com.cai.work.bean.respond.ConfigerRespond;
import com.cai.work.bean.respond.DiscoverRespond;
import com.cai.work.bean.respond.LoginRespond;
import com.cai.work.bean.respond.MessageRespond;
import com.cai.work.bean.respond.MineRespond;
import com.cai.work.bean.respond.NicknameRespond;
import com.cai.work.bean.respond.PhoneCodeRespond;
import com.cai.work.bean.respond.RecordRespond;
import com.cai.work.bean.respond.Respond;
import com.cai.work.bean.respond.WelfareRespond;
import com.cai.work.dao.UserDAO;
import com.example.clarence.utillibrary.PackageUtils;
import com.example.clarence.utillibrary.UniqueIdUtils;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.Lazy;
import io.reactivex.Flowable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
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
    public Lazy<UserDAO> userDAO;

    @Inject
    public RequestStore() {
    }

    /**
     * post 参数封装
     *
     * @param params
     * @return
     */
    public RequestBody getRequestBody(Map<String, String> params) {
        if (params == null || params.size() == 0) {
            return null;
        }
        String jsonString = getJson(params);
        return RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), jsonString);
    }

    /**
     * 参数转json
     *
     * @param params
     * @return
     */
    public String getJson(Map<String, String> params) {
        if (params == null || params.size() == 0) {
            return null;
        }
        JSONObject jsonObject = new JSONObject();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            jsonObject.put(entry.getKey(), entry.getValue());
        }
        return jsonObject.toJSONString();
    }

    /**
     * 共用请求头
     *
     * @param headerMap
     * @return
     */
    public Map<String, String> getRequestHeader(Map<String, String> headerMap) {
        if (headerMap == null) {
            headerMap = new HashMap<>();
        }
        Context context = App.getAppContext();
        headerMap.put("version", PackageUtils.getVersionName(context));
        headerMap.put("platform", "2");
        headerMap.put("lang", LanguageLocalUtil.getSystemLanguage().toLowerCase());
        String token = userDAO.get().getToken();
        if (!TextUtils.isEmpty(token)) {
            headerMap.put("auth", token);
        }
        try {
            headerMap.put("openudid", UniqueIdUtils.getDeviceInfo(context, UniqueIdUtils.DEVICES_INFO.ANDROID_ID));
            headerMap.put("imei", UniqueIdUtils.getDeviceInfo(context, UniqueIdUtils.DEVICES_INFO.IMEI));
        } catch (Exception e) {
            e.printStackTrace();
        }
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
     * 领取糖果
     *
     * @return
     */
    public Flowable<Respond> receiveCandy(Map<String, String> params) {
        Flowable<Respond> flowable = retrofit.get().create(ApiService.class)
                .receiveCandy(getRequestHeader(), getRequestBody(params))
                .subscribeOn(Schedulers.newThread());
        return flowable;
    }

    /**
     * 获取发现列表
     *
     * @return
     */
    public Flowable<DiscoverRespond> questDiscoverList() {
        Flowable<DiscoverRespond> flowable = retrofit.get().create(ApiService.class)
                .questDiscoverList(getRequestHeader())
                .subscribeOn(Schedulers.newThread());
        return flowable;
    }

    /**
     * 获取验证码
     *
     * @return
     */
    public Flowable<PhoneCodeRespond> getPhoneCode(Map<String, String> params) {
        Flowable<PhoneCodeRespond> flowable = retrofit.get().create(ApiService.class)
                .getPhoneCode(getRequestHeader(), getRequestBody(params))
                .subscribeOn(Schedulers.newThread());
        return flowable;
    }


    /**
     * 登录和注册
     *
     * @return
     */
    public Flowable<LoginRespond> loginOrRegister(Map<String, String> params) {
        Flowable<LoginRespond> flowable = retrofit.get().create(ApiService.class)
                .loginOrRegister(getRequestHeader(), getRequestBody(params))
                .subscribeOn(Schedulers.newThread());
        return flowable;
    }

    /**
     * 加载消息
     *
     * @return
     */
    public Flowable<MessageRespond> loadMsgData() {
        Flowable<MessageRespond> flowable = retrofit.get().create(ApiService.class)
                .loadMsgData(getRequestHeader())
                .subscribeOn(Schedulers.newThread());
        return flowable;
    }

    /**
     * 加载配置-- 为7牛上传图片用
     *
     * @return
     */
    public Flowable<ConfigerRespond> laodConfiguration() {
        Flowable<ConfigerRespond> flowable = retrofit.get().create(ApiService.class)
                .laodConfiguration(getRequestHeader())
                .subscribeOn(Schedulers.newThread());
        return flowable;
    }

    /**
     * 更新昵称
     *
     * @return
     */
    public Flowable<NicknameRespond> upUserNickName(Map<String, String> params) {
        Flowable<NicknameRespond> flowable = retrofit.get().create(ApiService.class)
                .upUserNickName(getRequestHeader(), getRequestBody(params))
                .subscribeOn(Schedulers.newThread());
        return flowable;
    }

    /**
     * 获取国家编号
     *
     * @return
     */
    public Flowable<ResponseBody> loadNationCode() {
        Flowable<ResponseBody> flowable = retrofit.get().create(ApiService.class)
                .loadNationCode(getRequestHeader())
                .subscribeOn(Schedulers.newThread());
        return flowable;
    }


    /**
     * 获取糖果详情页数据
     *
     * @return
     */
    public Flowable<ResponseBody> loadCandyDetail(Map<String, String> params) {
        Flowable<ResponseBody> flowable = retrofit.get().create(ApiService.class)
                .loadCandyDetail(getRequestHeader(), params)
                .subscribeOn(Schedulers.newThread());
        return flowable;
    }

    /**
     * 获取记录数据
     *
     * @return
     */
    public Flowable<RecordRespond> loadRecord(Map<String, String> params) {
        Flowable<RecordRespond> flowable = retrofit.get().create(ApiService.class)
                .loadRecord(getRequestHeader(), params)
                .subscribeOn(Schedulers.newThread());
        return flowable;
    }

    /**
     * 请求福利
     *
     * @return
     */
    public Flowable<WelfareRespond> requestWelfare() {
        Flowable<WelfareRespond> flowable = retrofit.get().create(ApiService.class)
                .requestWelfare(getRequestHeader())
                .subscribeOn(Schedulers.newThread());
        return flowable;
    }

    /**
     * 我的资产
     *
     * @return
     */
    public Flowable<AssetRespond> requestAsset(Map<String, String> params) {
        Flowable<AssetRespond> flowable = retrofit.get().create(ApiService.class)
                .requestAsset(getRequestHeader(),params)
                .subscribeOn(Schedulers.newThread());
        return flowable;
    }
}
