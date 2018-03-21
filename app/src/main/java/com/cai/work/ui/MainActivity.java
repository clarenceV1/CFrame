package com.cai.work.ui;

import android.app.ActivityOptions;
import android.content.Intent;
import android.transition.Explode;
import android.transition.Slide;
import android.view.Gravity;
import android.view.View;

import com.cai.annotation.apt.Router;
import com.cai.annotation.aspect.CostTime;
import com.cai.annotation.aspect.SingleClick;
import com.cai.apt.TRouter;
import com.cai.framework.base.BaseLifecycleObserver;
import com.cai.module.a.AmoduleActivity;
import com.cai.work.R;
import com.cai.work.base.BaseActivity;
import com.cai.work.base.Jumpter;
import com.cai.work.bean.Weather;
import com.cai.work.databinding.MainBinding;
import com.cai.work.ui.presenter.MainPresenter;
import com.cai.work.ui.presenter.MainView;

@Router(Jumpter.HOME)
public class MainActivity extends BaseActivity<MainPresenter, MainBinding> implements MainView {

    @Override
    public int getLayoutId() {
        return R.layout.main;
    }

    @Override
    @CostTime
    public void initView() {
//        Slide slide=new Slide();
//        slide.setDuration(3000);
//        getWindow().setEnterTransition(slide);
//        getWindow().setReenterTransition(new Explode().setDuration(600));
        setData(BaseLifecycleObserver.CLASS_NAME, "MainActivity");
    }


    @SingleClick
    public void goToWelcome(View view) {
//        TRouter.go(Jumpter.WELCOME);
//        finish();
        //测试过渡动画
//        View searchView = MainActivity.this.findViewById(R.id.searchView);
//        Intent intent = new Intent(this, WelcomeActivity.class);
//        ActivityOptions transitionActivityOptions = ActivityOptions.makeSceneTransitionAnimation(this, searchView, "shared_image_");
//        startActivity(intent, transitionActivityOptions.toBundle());
        Intent intent = new Intent(this, AmoduleActivity.class);
        startActivity(intent);
    }

    @Override
    public void setMainContent(String content) {
        mViewBinding.btn.setText(content);
    }

    @Override
    public void showWeather(Weather weather) {
        mViewBinding.tvWeather.setText(weather.toString());
    }
}
