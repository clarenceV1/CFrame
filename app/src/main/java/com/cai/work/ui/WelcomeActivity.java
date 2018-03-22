package com.cai.work.ui;

import android.Manifest;
import android.app.ActivityOptions;
import android.content.Intent;
import android.view.View;

import com.cai.annotation.apt.Router;
import com.cai.annotation.aspect.CheckLogin;
import com.cai.annotation.aspect.Permission;
import com.cai.apt.TRouter;
import com.cai.work.R;
import com.cai.work.base.AppBaseActivity;
import com.cai.work.base.Jumpter;
import com.cai.work.databinding.WelcomeBinding;
import com.cai.work.ui.presenter.WelcomePresenter;
import com.cai.work.ui.presenter.WelcomeView;

@Router(Jumpter.WELCOME)
public class WelcomeActivity extends AppBaseActivity<WelcomePresenter, WelcomeBinding> implements WelcomeView {

    @Override
    public int getLayoutId() {
        return R.layout.welcome;
    }

    @Override
    public void initView() {

    }

    public void goToWelcome(View view) {
        View searchView = WelcomeActivity.this.findViewById(R.id.searchView);
        Intent intent = new Intent(this, MainActivity.class);
        ActivityOptions transitionActivityOptions = ActivityOptions.makeSceneTransitionAnimation(this, searchView, "shared_image_");
        startActivity(intent, transitionActivityOptions.toBundle());
    }

    @Permission(Manifest.permission.CAMERA)
    @CheckLogin
    public void goToMain(View view) {
        TRouter.go(Jumpter.HOME);
    }

    @Override
    public void setContent(String content) {
        mViewBinding.btn.setText(content);
    }

}
