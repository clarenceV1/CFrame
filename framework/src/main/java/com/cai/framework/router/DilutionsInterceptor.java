package com.cai.framework.router;

/**
 * Created by clarence on 2018/2/5.
 */

public interface DilutionsInterceptor<T> {
    public boolean interceptor(DilutionsData<T> data);
}

