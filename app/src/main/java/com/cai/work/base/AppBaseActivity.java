package com.cai.work.base;


import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;

import com.cai.framework.base.GodBasePresenterActivity;
import com.cai.work.R;
import com.readystatesoftware.systembartint.SystemBarTintManager;

/**
 * Created by clarence on 2018/1/12.
 */

public abstract class AppBaseActivity<M extends ViewDataBinding> extends GodBasePresenterActivity<M> {
    @Override
    public void setStatusBar(SystemBarTintManager tintManager) {
        tintManager.setTintColor(getResources().getColor(R.color.ys_34_34_34));
    }
}
