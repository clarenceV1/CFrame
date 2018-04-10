package com.cai.work.base;


import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;

import com.cai.framework.base.GodBasePresenter;
import com.cai.framework.base.GodBasePresenterActivity;
import com.cai.work.utils.InstanceUtil;

/**
 * Created by clarence on 2018/1/12.
 */

public abstract class AppBaseActivity<P extends GodBasePresenter, M extends ViewDataBinding> extends GodBasePresenterActivity<P, M> {
    @Override
    public P getPresenter(Class presenterClass) {
        return InstanceUtil.getInstance(presenterClass);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
    }
}
