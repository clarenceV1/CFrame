package com.cai.framework.base;


import android.databinding.ViewDataBinding;
import android.os.Bundle;

import java.lang.reflect.ParameterizedType;

/**
 * Created by clarence on 2018/1/11.
 */

public abstract class CBaseActivity<P extends CBasePresenter, B extends ViewDataBinding> extends DataBindingActivity<B> {
    public P mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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


    public abstract P getPresenter(Class mPresenterClass);

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) mPresenter.onDetached();
    }
}
