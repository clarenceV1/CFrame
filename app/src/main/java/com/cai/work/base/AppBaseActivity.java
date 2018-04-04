package com.cai.work.base;


import android.databinding.ViewDataBinding;

import com.cai.framework.base.GodBasePresenter;
import com.cai.framework.base.GodBasePresenterActivity;
import com.cai.work.ui.presenter.LoginPresenter;
import com.cai.work.utils.InstanceUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by clarence on 2018/1/12.
 */

public abstract class AppBaseActivity<P extends GodBasePresenter, M extends ViewDataBinding> extends GodBasePresenterActivity<P, M> {
    @Override
    public P getPresenter(Class presenterClass) {
        return InstanceUtil.getInstance(presenterClass);
    }
}
