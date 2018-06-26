package com.cai.work.ui.welcome;

import android.os.Bundle;
import android.view.ViewTreeObserver;
import android.view.WindowManager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.cai.framework.base.GodBasePresenter;
import com.cai.framework.bean.TitleBarLayout;
import com.cai.work.R;
import com.cai.work.base.AppBaseActivity;
import com.cai.work.dagger.component.DaggerAppComponent;
import com.cai.work.databinding.WelcomeBinding;
import com.example.clarence.utillibrary.DeviceUtils;

import java.util.List;

import javax.inject.Inject;

@Route(path = "/AppModule/WelcomeActivity", name = "欢迎页面")
public class WelcomeActivity extends AppBaseActivity<WelcomeBinding> implements WelcomeView {

    @Inject
    WelcomePresenter presenter;
    long time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);// 设置全屏
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initDagger() {
        DaggerAppComponent.create().inject(this);
    }

    @Override
    public void addPresenters(List<GodBasePresenter> observerList) {
        observerList.add(presenter);
    }

    @Override
    public int getLayoutId() {
        return R.layout.welcome;
    }

    @Override
    public void initView() {
        fitImage();
        time = System.currentTimeMillis();
        presenter.loadUpgrade();
        mViewBinding.tvTitleBar.setTitleText("huanying");
//        TitleBarLayout tvTitleBar = () findViewById(R.id.tvTitleBar);
    }

    private void fitImage() {
        ViewTreeObserver vto = mViewBinding.imgTitle.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mViewBinding.imgTitle.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                int screenHeight = DeviceUtils.getScreenHeight(WelcomeActivity.this);
                float height = screenHeight * 0.46f;
                int imgeHeight = mViewBinding.imgTitle.getHeight();
                mViewBinding.imgTitle.setY(height - imgeHeight / 2);
            }
        });
    }

    @Override
    public void appUpdate() {
        long timeMillis = 3000 - (System.currentTimeMillis() - time);
        if (timeMillis > 0) {
            mViewBinding.imgTitle.postDelayed(new Runnable() {
                @Override
                public void run() {
                    goMainActivity();
                }
            }, timeMillis);
        } else {
            goMainActivity();
        }
    }

    private void goMainActivity() {
//        dd
    }
}
