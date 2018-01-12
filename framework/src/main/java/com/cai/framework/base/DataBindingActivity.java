package com.cai.framework.base;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatDelegate;
import android.view.View;

import com.cai.framework.utils.PermissionUtils;

public abstract class DataBindingActivity<B extends ViewDataBinding> extends FragmentActivity {
    public Context mContext;
    public B mViewBinding;

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View rootView = getLayoutInflater().inflate(this.getLayoutId(), null, false);
        mViewBinding = DataBindingUtil.bind(rootView);
        setContentView(rootView);
        mContext = this;
        initPresenter();
        initView();
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        PermissionUtils.onRequestPermissionsResult(requestCode, permissions, grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    public abstract void initPresenter();

    public abstract int getLayoutId();

    public abstract void initView();
}
