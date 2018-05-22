package com.cai.work.ui.web;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.cai.framework.web.WebViewFragment;
import com.cai.work.R;
import com.cai.work.base.AppBaseActivity;
import com.cai.work.dagger.component.DaggerAppComponent;
import com.cai.work.databinding.WebBinding;

import java.util.List;

import javax.inject.Inject;

@Route(path = "/AppModule/WebActivity", name = "首页")
public class WebActivity extends AppBaseActivity<WebBinding> implements WebForRTB {

    @Inject
    WebPresenterForRTB webPresenterForRTB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        DaggerAppComponent.create().inject(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void addPresenters(List observerList) {
        observerList.add(webPresenterForRTB);
    }

    @Override
    public int getLayoutId() {
        return R.layout.web;
    }

    @Override
    public void initView() {
        initFragment();
    }

    private void initFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment fragment = getWebFragment();
        fragmentTransaction.replace(R.id.containLayout, fragment);
        fragmentTransaction.commit();
    }

    public void goToMain(View view) {
        webPresenterForRTB.goToMain();
        finish();
    }

    public Fragment getWebFragment() {
        return Fragment.instantiate(this, WebViewFragment.class.getName());
    }
}
