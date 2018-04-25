package com.cai.work.ui.main;

import android.Manifest;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.facade.callback.NavigationCallback;
import com.alibaba.android.arouter.launcher.ARouter;
import com.cai.annotation.aspect.CostTime;
import com.cai.annotation.aspect.Permission;
import com.cai.annotation.aspect.SingleClick;
import com.cai.framework.base.GodBasePresenter;
import com.cai.framework.manager.LogDock;
import com.cai.work.R;
import com.cai.work.base.AppBaseActivity;
import com.cai.work.bean.Weather;
import com.cai.work.dagger.component.DaggerAppComponent;
import com.cai.work.databinding.MainBinding;
import com.cai.work.ui.Help;
import com.example.clarence.imageloaderlibrary.ILoadImage;
import com.example.clarence.imageloaderlibrary.ILoadImageParams;
import com.example.clarence.utillibrary.ToastUtils;

import java.util.List;

import javax.inject.Inject;

@Route(path = "/AppModule/MainActivity", name = "首页")
public class MainActivity extends AppBaseActivity<MainBinding> implements MainViewForRTB, MainViewForAD, MainViewForSA {
    @Inject
    ILoadImage imageLoader;
    @Autowired
    String name = "Default";

    @Inject
    MainPresenterForRTB mainPresenterForRTB;
    @Inject
    MainPresenterForAD mainPresenterForAD;
    @Inject
    MainPresenterForSA mainPresenterForSA;

    public String title = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        DaggerAppComponent.create().inject(this);
        super.onCreate(savedInstanceState);
        ARouter.getInstance().inject(this);
        showToast();
        Help.install().setContext(this);
    }

    private void showToast() {
        if (!TextUtils.isEmpty(name)) {
            ToastUtils.showShort(name);
        }
    }

    @Override
    public void addPresenters(List<GodBasePresenter> observerList) {
        observerList.add(mainPresenterForRTB);
        observerList.add(mainPresenterForAD);
        observerList.add(mainPresenterForSA);
    }

    @Override
    public int getLayoutId() {
        return R.layout.main;
    }

    @Override
    @CostTime
    public void initView() {

    }

    @SingleClick
    @Permission({Manifest.permission.CAMERA})
    public void goToBModule(View view) {
        ARouter.getInstance().build("/BModule/BModuleActivity").navigation(this, new NavigationCallback() {
            @Override
            public void onFound(Postcard postcard) {
                LogDock.getLog().debug("Postcard", "onFound");
            }

            @Override
            public void onLost(Postcard postcard) {
                LogDock.getLog().debug("Postcard", "onLost");
            }

            @Override
            public void onArrival(Postcard postcard) {
                LogDock.getLog().debug("Postcard", "onArrival");
            }

            @Override
            public void onInterrupt(Postcard postcard) {
                LogDock.getLog().debug("Postcard", "onInterrupt");
            }
        });
        finish();
    }

    @SingleClick
    public void goToAModule(View view) {
        ARouter.getInstance().build("/AModule/AModuleActivity").navigation();
        finish();
    }

    @Override
    public void showWeather(Weather weather) {
        mViewBinding.tvWeather.setText(weather.toString());
    }

    @Override
    public void showWeatherError(String error) {
        mViewBinding.tvWeather.setText(error);
    }

    @Override
    public void showImage(ILoadImageParams imageParams) {
        imageParams.setImageView(mViewBinding.imgTest);
        imageLoader.loadImage(this, imageParams);
    }

    @Override
    public void showAD(String data) {

    }

    @Override
    public void showSA(String data) {

    }
}
