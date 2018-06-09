package com.cai.work;

import com.cai.work.bean.LoginRequest;
import com.cai.work.bean.home.HomeData;

import io.reactivex.Flowable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by clarence on 2018/1/12.
 */

public interface ApiService {

    @GET("/app/index")
    Flowable<HomeData> getHomeData();

    @POST("app/login/index")
    @FormUrlEncoded
    Flowable<LoginRequest> requestLogin(@Field("username") String username, @Field("password") String password);
}
