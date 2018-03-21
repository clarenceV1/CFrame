package com.cai.framework.base;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;
import android.content.Context;

import com.cai.framework.utils.log.LogUtils;

import java.util.Map;

/**
 * Created by clarence on 2018/3/21.
 */
public class BaseLifecycleObserver implements LifecycleObserver {
    Lifecycle lifecycle;
    Context context;
    Map<String, Object> data;
    public static final String CLASS_NAME = "className";
    boolean isDebug;

    public BaseLifecycleObserver(Context context, Lifecycle lifecycle, Map<String, Object> data) {
        this.lifecycle = lifecycle;
        this.context = context;
        this.data = data;
        isDebug = BaseConfig.getInsatance().isDebug();
    }

    private String getClassName() {
        if (data != null && data.get(CLASS_NAME) != null) {
            return data.get(CLASS_NAME).toString();
        }
        return "请设置类名称";
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    public void ON_CREATE() {
        if (isDebug) {
            LogUtils.getInsatance().debug("LifecycleObserver",getClassName() , ":ON_CREATE");
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    public void ON_START() {
        if (isDebug) {
            LogUtils.getInsatance().debug("LifecycleObserver",getClassName() , ":ON_START");
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void ON_RESUME() {
        if (isDebug) {
            LogUtils.getInsatance().debug("LifecycleObserver",getClassName() , ":ON_RESUME");
        }

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public void ON_PAUSE() {
        if (isDebug) {
            LogUtils.getInsatance().debug("LifecycleObserver",getClassName() , ":ON_PAUSE");
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    public void ON_STOP() {
        if (isDebug) {
            LogUtils.getInsatance().debug("LifecycleObserver",getClassName() , ":ON_STOP");
        }

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void ON_DESTROY() {
        if (isDebug) {
            LogUtils.getInsatance().debug("LifecycleObserver",getClassName() , ":ON_DESTROY");
        }
    }
}