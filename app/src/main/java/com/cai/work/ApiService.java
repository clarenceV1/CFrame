package com.cai.work;

import com.cai.work.bean.User;

import io.reactivex.Flowable;
import retrofit2.http.GET;

/**
 * Created by clarence on 2018/1/12.
 */

public interface ApiService {
    @GET("login")
    public Flowable<User> login();
}
