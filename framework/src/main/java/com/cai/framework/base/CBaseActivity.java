package com.cai.framework.base;


import android.arch.lifecycle.LifecycleRegistry;
import android.arch.lifecycle.LifecycleRegistryOwner;
import android.databinding.ViewDataBinding;
import android.os.Bundle;

import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by clarence on 2018/1/11.
 */

public abstract class CBaseActivity<P extends CBasePresenter, B extends ViewDataBinding> extends DataBindingActivity<B> implements LifecycleRegistryOwner {
    public P mPresenter;
    private final LifecycleRegistry mRegistry = new LifecycleRegistry(this);
    private Map<String, Object> data = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLifecycle().addObserver(new BaseLifecycleObserver(this, mRegistry, data));
    }

    @Override
    public void initPresenter() {
        if (this instanceof BaseView && this.getClass().getGenericSuperclass() instanceof ParameterizedType &&
                ((ParameterizedType) (this.getClass().getGenericSuperclass())).getActualTypeArguments().length > 0) {
            Class presenterClass = (Class) ((ParameterizedType) (this.getClass().getGenericSuperclass())).getActualTypeArguments()[0];
            try {
                mPresenter = getPresenter(presenterClass);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (mPresenter != null) {
                mPresenter.setView(this);
            }
        }
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

    public abstract P getPresenter(Class mPresenterClass);

    @Override
    public LifecycleRegistry getLifecycle() {
        return mRegistry;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) mPresenter.onDetached();
    }
}
