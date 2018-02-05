package com.cai.framework.watcher;

/**
 * Created by clarence on 2018/2/5.
 */

public class WatchParam {
    public Object[] args;
    public String methodName;

    public WatchParam(Object[] args, String methodName) {
        this.args = args;
        this.methodName = methodName;
    }
}
