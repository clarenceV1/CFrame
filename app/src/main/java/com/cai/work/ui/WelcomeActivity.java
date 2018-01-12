package com.cai.work.ui;

import android.Manifest;
import android.view.View;

import com.cai.annotation.apt.Router;
import com.cai.annotation.aspect.Permission;
import com.cai.apt.TRouter;
import com.cai.framework.BaseActivity;
import com.cai.work.R;
import com.cai.work.base.Jumpter;
import com.cai.work.databinding.WelcomeBinding;
import com.cai.work.ui.presenter.WelcomePresenter;
import com.cai.work.ui.presenter.WelcomeView;

@Router(Jumpter.WELCOME)
public class WelcomeActivity extends BaseActivity<WelcomePresenter, WelcomeBinding> implements WelcomeView {

    @Override
    public int getLayoutId() {
        return R.layout.welcome;
    }

    @Override
    public void initView() {

    }

    @Permission(Manifest.permission.CAMERA)
    public void goToMain(View view) {
        TRouter.go(Jumpter.HOME);
        finish();
    }

    @Override
    public void setContent(String content) {
        mViewBinding.btn.setText(content);
    }
}
