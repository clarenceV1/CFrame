package com.cai.work.common;

import com.cai.work.bean.respond.AppUpdateResond;

import io.reactivex.Flowable;
import retrofit2.http.GET;

/**
 * Created by clarence on 2018/1/12.
 */

public interface ApiService {

    @GET("upgrade")
    Flowable<AppUpdateResond> getHomeData();
//
//    @POST("/app/login/index")
//    @FormUrlEncoded
//    Flowable<LoginRespond> requestLogin(@Field("username") String username, @Field("password") String password);
}
