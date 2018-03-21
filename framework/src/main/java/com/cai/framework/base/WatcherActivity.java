package com.cai.framework.base;

import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.LifecycleRegistry;
import android.arch.lifecycle.LifecycleRegistryOwner;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by clarence on 2018/2/5.
 */

public class WatcherActivity extends FragmentActivity implements LifecycleRegistryOwner {
    private Map<String, Object> data = new HashMap<>();
    private final LifecycleRegistry mRegistry = new LifecycleRegistry(this);
    BaseLifecycleObserver baseLifecycleObserver;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        baseLifecycleObserver = new BaseLifecycleObserver(this, mRegistry, data);
        addLifeCycleObserver(baseLifecycleObserver);
    }

    @Override
    public LifecycleRegistry getLifecycle() {
        return mRegistry;
    }

    public void addLifeCycleObserver(LifecycleObserver observer) {
        getLifecycle().addObserver(observer);
    }

    /**
     * 应用层自己传参数
     *
     * @param key   @link(com.cai.framework.base.BaseLifecycleObserver#CLASS_NAME) 类名称（用于生命周期）
     * @param value
     */
    public void setData(String key, Object value) {
        this.data.put(key, value);
    }
}
