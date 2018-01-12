package com.cai.work.base;


import android.databinding.ViewDataBinding;

import com.cai.framework.base.CBaseActivity;
import com.cai.framework.base.CBasePresenter;
import com.cai.work.utils.InstanceUtil;

/**
 * Created by clarence on 2018/1/12.
 */

public abstract class BaseActivity<P extends CBasePresenter, B extends ViewDataBinding> extends CBaseActivity<P, B> {
    @Override
    public P getPresenter(Class presenterClass) {
        return InstanceUtil.getInstance(presenterClass);
    }
}
