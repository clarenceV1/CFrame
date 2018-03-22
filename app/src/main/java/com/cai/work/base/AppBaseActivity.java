package com.cai.work.base;


import android.databinding.ViewDataBinding;

import com.cai.framework.base.BaseLifecycleObserver;
import com.cai.framework.base.GodBasePresenter;
import com.cai.framework.base.WatcherActivity;
import com.cai.work.utils.InstanceUtil;

/**
 * Created by clarence on 2018/1/12.
 */

public abstract class AppBaseActivity<L extends BaseLifecycleObserver, P extends GodBasePresenter, M extends ViewDataBinding> extends WatcherActivity<L, P, M> {
    @Override
    public P getPresenter(Class presenterClass) {
        return InstanceUtil.getInstance(presenterClass);
    }

    @Override
    public L getLifecycleObserver(Class presenterClass) {
        return InstanceUtil.getInstance(presenterClass);
    }
}
