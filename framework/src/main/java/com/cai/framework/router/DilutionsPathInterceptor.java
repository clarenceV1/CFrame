package com.cai.framework.router;

/**
 * Created by clarence on 2018/2/5.
 */
public abstract class DilutionsPathInterceptor implements DilutionsInterceptor{

    public final static int LEVEL_NORMAL = 0;

    public int level(){
        return LEVEL_NORMAL;
    }
}
