package com.cai.work.ui;

import android.arch.lifecycle.LifecycleRegistry;
import android.view.View;

import com.cai.annotation.apt.Router;
import com.cai.annotation.aspect.CostTime;
import com.cai.annotation.aspect.SingleClick;
import com.cai.apt.TRouter;
import com.cai.framework.base.BaseLifecycleObserver;
import com.cai.work.R;
import com.cai.work.base.BaseActivity;
import com.cai.work.base.Jumpter;
import com.cai.work.bean.Weather;
import com.cai.work.databinding.MainBinding;
import com.cai.work.ui.lifecycleobserver.MainObserver;
import com.cai.work.ui.presenter.MainPresenter;
import com.cai.work.ui.presenter.MainView;

import java.util.Map;

@Router(Jumpter.HOME)
public class MainActivity extends BaseActivity<MainPresenter, MainBinding,MainObserver> implements MainView {

    @Override
    public int getLayoutId() {
        return R.layout.main;
    }

    @Override
    @CostTime
    public void initView() {
        setData(BaseLifecycleObserver.CLASS_NAME, "MainActivity");
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
    public MainObserver getLifecycleObserver(LifecycleRegistry mRegistry, Map<String, Object> data) {
        return new MainObserver(this, mRegistry, data);
    }
}
