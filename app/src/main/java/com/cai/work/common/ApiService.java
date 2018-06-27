package com.cai.work.common;

import com.cai.work.bean.respond.AppUpdateResond;

import java.util.Map;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;

/**
 * Created by clarence on 2018/1/12.
 */

public interface ApiService {

    @GET("/upgrade")
    Flowable<AppUpdateResond> loadUpgrade(@HeaderMap Map<String,String> map);

    @GET("/mine")
    Flowable<AppUpdateResond> loadMineData(@HeaderMap Map<String,String> map);
//
//    @POST("/app/login/index")
//    @FormUrlEncoded
//    Flowable<LoginRespond> requestLogin(@Field("username") String username, @Field("password") String password);
}
