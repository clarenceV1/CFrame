package com.cai.work;

import com.cai.work.bean.User;
import com.cai.work.bean.Weather;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by clarence on 2018/1/12.
 */

public interface ApiService {
    @GET("login")
    public Flowable<User> login();

    @GET("query")
    public Flowable<Weather> getWeather(@Query("cityname") String cityname,@Query("key") String key);
}
