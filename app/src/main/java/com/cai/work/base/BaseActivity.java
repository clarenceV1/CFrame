package com.cai.work.base;


import android.databinding.ViewDataBinding;

import com.cai.framework.base.BaseLifecycleObserver;
import com.cai.framework.base.GodBaseActivity;
import com.cai.framework.base.GodBasePresenter;
import com.cai.work.utils.InstanceUtil;

/**
 * Created by clarence on 2018/1/12.
 */

public abstract class BaseActivity<P extends GodBasePresenter, M extends ViewDataBinding, L extends BaseLifecycleObserver> extends GodBaseActivity<P, M, L> {
    @Override
    public P getPresenter(Class presenterClass) {
        return InstanceUtil.getInstance(presenterClass);
    }
}
