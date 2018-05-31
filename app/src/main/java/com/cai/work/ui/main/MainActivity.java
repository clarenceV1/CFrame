package com.cai.work.ui.main;

import android.Manifest;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.cai.annotation.apt.Bind;
import com.cai.annotation.aspect.CostTime;
import com.cai.annotation.aspect.Permission;
import com.cai.annotation.aspect.SingleClick;
import com.cai.framework.base.GodBasePresenter;
import com.cai.framework.base.ViewInjector;
import com.cai.lib.logger.Logger;
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

    @Bind(R.id.tvWeather)
    public TextView tvWeather;

    public String title = "MainActivity";
    private float mTrancetAnimh = -3f;
    private float mTrancetAnimw = -3f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        DaggerAppComponent.create().inject(this);
        super.onCreate(savedInstanceState);
        ARouter.getInstance().inject(this);
        ViewInjector.injectView(this);
        tvWeather.setText("ddd");
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
        ValueAnimator valueAnimator = getTrancetAnimh(mViewBinding.imgTest);
        valueAnimator.start();
    }

    public ValueAnimator getTrancetAnimh(final View view) {
        ValueAnimator valueAnimator = ValueAnimator.ofInt(5400);
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.setRepeatMode(ValueAnimator.RESTART);
        valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (int) animation.getAnimatedValue();

                if (value <= 1800) {
                    float fragment =  value * 6.0f / 1800;
                    mTrancetAnimw = -3 + fragment;
                    Log.d("ValueAnimator","右移 value ==>" + value +"  fragment ==> "+fragment);
                } else if (value <= 3600) {
                    float fragment = (value - 1800) * 6.0f / 1800;
                    mTrancetAnimw = 3 - fragment;
                    mTrancetAnimh = -3 + fragment;
                    Log.d("ValueAnimator","下移 value ==>" + value +"  fragment ==> "+fragment);
                } else {
                    float fragment = (value - 3600) * 6.0f / 1800;
                    mTrancetAnimh = 3 - fragment;
                    Log.d("ValueAnimator","回去 value ==>" + value +"  fragment ==> "+fragment);
                }
                view.setTranslationY(mTrancetAnimh);
                view.setTranslationX(mTrancetAnimw);
                Log.d("ValueAnimator","vaule ==> " + value +" x ==> "+mTrancetAnimw+" y ==> "+mTrancetAnimh);
            }
        });

        valueAnimator.setDuration(5400);
        return valueAnimator;
    }

    public void goToWeb(View view) {
        mainPresenterForRTB.goToWeb();
        finish();
    }

    @SingleClick
    @Permission({Manifest.permission.CAMERA})
    public void goToBModule(View view) {
        mainPresenterForRTB.goToBModule();
        finish();
    }

    @SingleClick
    public void goToAModule(View view) {
        mainPresenterForRTB.goToAModule();
        finish();
    }

    @SingleClick
    public void goToListViewActivity(View view) {
        mainPresenterForRTB.goToListViewActivity();
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
