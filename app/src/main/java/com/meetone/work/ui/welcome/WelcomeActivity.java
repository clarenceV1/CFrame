package com.meetone.work.ui.welcome;

import android.os.Bundle;
import android.view.ViewTreeObserver;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.cai.framework.base.GodBasePresenter;
import com.meetone.work.R;
import com.meetone.work.databinding.WelcomeBinding;
import com.example.clarence.utillibrary.DeviceUtils;
import com.meetone.work.base.App;
import com.meetone.work.base.AppBaseActivity;

import java.util.List;

import javax.inject.Inject;

@Route(path = "/MeetOne/WelcomeActivity", name = "欢迎页面")
public class WelcomeActivity extends AppBaseActivity<WelcomeBinding> implements WelcomeView {

    @Inject
    WelcomePresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);// 设置全屏
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initDagger() {
        App.getAppComponent().inject(this);
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
        presenter.createShortCut();
        presenter.loadUpgrade();
        presenter.loadMineData();
        goMainActivity();
    }

    private void goMainActivity() {
        mViewBinding.imgTitle.postDelayed(new Runnable() {
            @Override
            public void run() {
                ARouter.getInstance().build("/MeetOne/MainActivity").navigation();
                finish();
            }
        }, 3000);
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
}
