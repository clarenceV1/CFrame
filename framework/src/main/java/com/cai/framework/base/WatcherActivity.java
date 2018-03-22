package com.cai.framework.base;

import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.LifecycleRegistry;
import android.arch.lifecycle.LifecycleRegistryOwner;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;

import java.lang.reflect.ParameterizedType;

/**
 * Created by clarence on 2018/2/5.
 */

public abstract class WatcherActivity<L extends BaseLifecycleObserver,P extends GodBasePresenter, M extends ViewDataBinding> extends GodBasePresenterActivity<P, M> implements LifecycleRegistryOwner {
    private final LifecycleRegistry mRegistry = new LifecycleRegistry(this);
    L lifecycleObserver;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initLifeCycleObserver();
    }

    protected void initLifeCycleObserver() {
        if (this.getClass().getGenericSuperclass() instanceof ParameterizedType &&
                ((ParameterizedType) (this.getClass().getGenericSuperclass())).getActualTypeArguments().length > 0) {
            Class lifecycleObserverClass = (Class) ((ParameterizedType) (this.getClass().getGenericSuperclass())).getActualTypeArguments()[0];
            try {
                lifecycleObserver = getLifecycleObserver(lifecycleObserverClass);
                lifecycleObserver.init(this, mRegistry);
                addLifeCycleObserver(lifecycleObserver);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public abstract L getLifecycleObserver(Class lifecycleObserverClass);

    @Override
    public LifecycleRegistry getLifecycle() {
        return mRegistry;
    }

    private void addLifeCycleObserver(LifecycleObserver observer) {
        if (observer == null) {
            return;
        }
        getLifecycle().addObserver(observer);
    }
}
