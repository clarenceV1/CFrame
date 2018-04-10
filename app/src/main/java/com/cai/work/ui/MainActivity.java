package com.cai.work.ui;

import android.os.Bundle;
import android.view.View;

import com.cai.annotation.apt.Router;
import com.cai.annotation.aspect.CostTime;
import com.cai.annotation.aspect.SingleClick;
import com.cai.apt.TRouter;
import com.cai.work.R;
import com.cai.work.base.AppBaseActivity;
import com.cai.work.base.Jumpter;
import com.cai.work.bean.Weather;
import com.cai.work.dagger.component.DaggerAppComponent;
import com.cai.work.databinding.MainBinding;
import com.cai.work.ui.presenter.MainPresenter;
import com.cai.work.ui.presenter.MainView;
import com.example.clarence.imageloaderlibrary.ILoadImage;
import com.example.clarence.imageloaderlibrary.ILoadImageParams;

import javax.inject.Inject;

@Router(Jumpter.HOME)
public class MainActivity extends AppBaseActivity<MainPresenter, MainBinding> implements MainView {

    @Inject
    ILoadImage imageLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        DaggerAppComponent.create().inject(this);
        super.onCreate(savedInstanceState);
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
    public void goToWelcome(View view) {
        TRouter.go(Jumpter.WELCOME);
        finish();
    }

    @Override
    public void setMainContent(String content) {
        mViewBinding.btn.setText(content);
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
}
