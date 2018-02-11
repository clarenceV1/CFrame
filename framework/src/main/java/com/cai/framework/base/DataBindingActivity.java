package com.cai.framework.base;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatDelegate;
import android.view.View;

import com.cai.framework.router.MeetyouDilutions;
import com.cai.framework.utils.PermissionUtils;

public abstract class DataBindingActivity<B extends ViewDataBinding> extends WatcherActivity {
    public Context mContext;
    public B mViewBinding;

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initRouter();
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

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        this.dilutionsNewIntent();
    }

    public void dilutionsNewIntent() {
        MeetyouDilutions meetyouDilutions = MeetyouDilutions.create();
        if (meetyouDilutions != null) {
            meetyouDilutions.onNewIntent(this);
        }
    }

    private void initRouter() {
        MeetyouDilutions meetyouDilutions = MeetyouDilutions.create();
        if (meetyouDilutions != null) {
            meetyouDilutions.register(this);
        }

    }

    public abstract void initPresenter();

    public abstract int getLayoutId();

    public abstract void initView();
}
