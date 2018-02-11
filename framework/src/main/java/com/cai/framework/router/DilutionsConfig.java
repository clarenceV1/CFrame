package com.cai.framework.router;

/**
 * Created by clarence on 2018/2/5.
 */

public class DilutionsConfig<T> {

    private DilutionsCallBack mDilutionsCallBack;
    private DilutionsInterceptor mDilutionsInterceptor;
    private T mWhat;

    private DilutionsConfig(Builder<T> builder) {
        this.mDilutionsCallBack = builder.mDilutionsCallBack;
        this.mDilutionsInterceptor = builder.mDilutionsInterceptor;
        this.mWhat = builder.mWhat;
    }

    public DilutionsCallBack getDilutionsCallBack() {
        return mDilutionsCallBack;
    }

    public DilutionsInterceptor getDilutionsInterceptor() {
        return mDilutionsInterceptor;
    }

    public T getWhat() {
        return mWhat;
    }

    public static class Builder<T> {
        private DilutionsCallBack mDilutionsCallBack;
        private DilutionsInterceptor mDilutionsInterceptor;
        private T mWhat;

        Builder() {
            // Doesn't use a setter as always required.
        }

        public Builder setDilutionsCallBack(DilutionsCallBack callBack) {
            mDilutionsCallBack = callBack;
            return this;
        }

        public Builder setDilutionsInterceptor(DilutionsInterceptor interceptor) {
            mDilutionsInterceptor = interceptor;
            return this;
        }

        public Builder setWhat(T what) {
            mWhat = what;
            return this;
        }

        public DilutionsConfig build() {
            return new DilutionsConfig(this);
        }
    }
}

