package com.cai.framework.router;

/**
 * Created by clarence on 2018/2/5.
 */

public class DilutionsConfigFactory {
    public static <T> DilutionsConfig.Builder newBuilder(DilutionsCallBack<T> dilutionsCallBack, DilutionsInterceptor<T> interceptor, T what) {
        return new DilutionsConfig.Builder<T>()
                .setDilutionsCallBack(dilutionsCallBack)
                .setDilutionsInterceptor(interceptor)
                .setWhat(what);
    }
}
