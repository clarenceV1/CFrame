package com.cai.work.ui;

import android.Manifest;
import android.view.View;

import com.cai.annotation.apt.Router;
import com.cai.annotation.aspect.Permission;
import com.cai.apt.TRouter;
import com.cai.framework.BaseActivity;
import com.cai.work.R;
import com.cai.work.base.Jumpter;

@Router(Jumpter.WELCOME)
public class WelcomeActivity extends BaseActivity {

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
}
