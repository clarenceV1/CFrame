package com.cai.work;

import com.cai.work.bean.respond.FundDetailRespond;
import com.cai.work.bean.respond.LoginRespond;
import com.cai.work.bean.respond.HomeRespond;
import com.cai.work.bean.respond.UserInfoRespond;

import io.reactivex.Flowable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by clarence on 2018/1/12.
 */

public interface ApiService {

    @GET("/app/index")
    Flowable<HomeRespond> getHomeData();

    @POST("app/login/index")
    @FormUrlEncoded
    Flowable<LoginRespond> requestLogin(@Field("username") String username, @Field("password") String password);

    @GET("/app/member/index")
    Flowable<UserInfoRespond> getUserInfo(@Query("token") String token);

    @GET("/app/journal/index")
    Flowable<FundDetailRespond> requestFundDetail(@Query("page") int page, @Query("token") String token);

}
