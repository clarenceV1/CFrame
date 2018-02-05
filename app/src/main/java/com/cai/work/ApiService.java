package com.cai.work;

import com.cai.work.bean.Weather;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by clarence on 2018/1/12.
 */

public interface ApiService {

    @GET("/open/api/weather/json.shtml")
    public Flowable<Weather> getWeather(@Query("city") String city);
}
