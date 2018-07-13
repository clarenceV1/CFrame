package com.cai.work.common;

import com.cai.work.bean.respond.AppUpdateResond;
import com.cai.work.bean.respond.AssetRespond;
import com.cai.work.bean.respond.CandyListRespond;
import com.cai.work.bean.respond.ConfigerRespond;
import com.cai.work.bean.respond.DiscoverRespond;
import com.cai.work.bean.respond.LoginRespond;
import com.cai.work.bean.respond.MessageRespond;
import com.cai.work.bean.respond.MineRespond;
import com.cai.work.bean.respond.NicknameRespond;
import com.cai.work.bean.respond.RecordRespond;
import com.cai.work.bean.respond.Respond;
import com.cai.work.bean.respond.WelfareRespond;

import java.util.Map;

import io.reactivex.Flowable;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

/**
 * Created by clarence on 2018/1/12.
 */

public interface ApiService {

    @GET("api/upgrade")
    Flowable<AppUpdateResond> loadUpgrade(@HeaderMap Map<String, String> map);

    @GET("api/mine")
    Flowable<MineRespond> loadMineData(@HeaderMap Map<String, String> map);

    @GET("api/candy")
    Flowable<CandyListRespond> questCandyList(@HeaderMap Map<String, String> map);

    @POST("api/candy")
    Flowable<Respond> receiveCandy(@HeaderMap Map<String, String> map, @Body RequestBody body);

    @GET("api/discover")
    Flowable<DiscoverRespond> questDiscoverList(@HeaderMap Map<String, String> map);

    @POST("api/vercode")
    Flowable<Respond> getPhoneCode(@HeaderMap Map<String, String> map, @Body RequestBody body);

    @POST("api/login")
    Flowable<LoginRespond> loginOrRegister(@HeaderMap Map<String, String> map, @Body RequestBody body);


    @GET("api/msgbox")
    Flowable<MessageRespond> loadMsgData(@HeaderMap Map<String, String> map);

    @GET("api/configuration")
    Flowable<ConfigerRespond> laodConfiguration(@HeaderMap Map<String, String> map);

    @POST("api/profile")
    Flowable<NicknameRespond> upUserNickName(@HeaderMap Map<String, String> map, @Body RequestBody body);


    @GET("api/vercode")
    Flowable<ResponseBody> loadNationCode(@HeaderMap Map<String, String> map);

    @GET("api/token")
    Flowable<ResponseBody> loadCandyDetail(@HeaderMap Map<String, String> headMap, @QueryMap Map<String, String> map);

    @GET("api/record")
    Flowable<RecordRespond> loadRecord(@HeaderMap Map<String, String> headMap, @QueryMap Map<String, String> map);

    @GET("api/welfare")
    Flowable<WelfareRespond> requestWelfare(@HeaderMap Map<String, String> headMap);

    @GET("api/myassets")
    Flowable<AssetRespond> requestAsset(@HeaderMap Map<String, String> headMap, @QueryMap Map<String, String> map);

//
//    @POST("/app/login/index")
//    @FormUrlEncoded
//    Flowable<LoginRespond> requestLogin(@Field("username") String username, @Field("password") String password);
}
