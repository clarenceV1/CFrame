package com.cai.framework.base;


import android.databinding.ViewDataBinding;

import java.lang.reflect.ParameterizedType;

/**
 * Created by clarence on 2018/1/11.
 */

public abstract class BaseActivity<P extends BasePresenter, B extends ViewDataBinding> extends DataBindingActivity<B> {
    public P mPresenter;

    @Override
    public void initPresenter() {
        if (this instanceof BaseView && this.getClass().getGenericSuperclass() instanceof ParameterizedType &&
                ((ParameterizedType) (this.getClass().getGenericSuperclass())).getActualTypeArguments().length > 0) {
            Class mPresenterClass = (Class) ((ParameterizedType) (this.getClass().getGenericSuperclass())).getActualTypeArguments()[0];
            try {
                mPresenter = (P) mPresenterClass.newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (mPresenter != null) {
                mPresenter.setView(this);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) mPresenter.onDetached();
    }
}
