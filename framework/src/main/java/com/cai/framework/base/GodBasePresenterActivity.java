package com.cai.framework.base;


import android.databinding.ViewDataBinding;
import android.os.Bundle;

import java.lang.reflect.ParameterizedType;

/**
 * Created by clarence on 2018/1/11.
 */

public abstract class GodBasePresenterActivity<P extends GodBasePresenter, M extends ViewDataBinding> extends DataBindingActivity<M> {
    public P mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initPresenter();
    }

    public void initPresenter() {
        if (this instanceof GodBaseView && this.getClass().getGenericSuperclass() instanceof ParameterizedType &&
                ((ParameterizedType) (this.getClass().getGenericSuperclass())).getActualTypeArguments().length > 0) {
            Class presenterClass = (Class) ((ParameterizedType) (this.getClass().getGenericSuperclass())).getActualTypeArguments()[1];
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

    public abstract P getPresenter(Class mPresenterClass);

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) mPresenter.onDetached();
    }
}
