package com.cai.work.ui.main;

import android.Manifest;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.cai.annotation.aspect.Permission;
import com.cai.framework.base.GodBasePresenter;
import com.cai.work.R;
import com.cai.work.base.App;
import com.cai.work.base.AppBaseActivity;
import com.cai.work.databinding.MainLayoutBinding;

import java.util.List;

import javax.inject.Inject;

@Route(path = "/AppModule/MainActivity", name = "主页面")
public class MainActivity extends AppBaseActivity<MainLayoutBinding> implements MainView {

    @Inject
    MainPresenter presenter;

    @Override
    public void initDagger() {
        App.getAppComponent().inject(this);
    }

    @Override
    public void addPresenters(List<GodBasePresenter> observerList) {
        observerList.add(presenter);
    }

    @Override
    public void initView() {
        requestPermission();
    }

    @Override
    public int getLayoutId() {
        return R.layout.main_layout;
    }

    public void jump(View view){
        ARouter.getInstance().build("/AppModule/WelcomeActivity").navigation();
        finish();
    }

    @Permission(value = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.ACCESS_WIFI_STATE})
    private void requestPermission() {

    }
}
