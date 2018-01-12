package com.cai.work.ui;

import android.view.View;

import com.cai.annotation.apt.Router;
import com.cai.apt.TRouter;
import com.cai.work.R;
import com.cai.work.base.BaseActivity;
import com.cai.work.base.Jumpter;
import com.cai.work.databinding.LoginBinding;
import com.cai.work.ui.presenter.LoginPresenter;
import com.cai.work.ui.presenter.LoginView;

@Router(Jumpter.LOGIN)
public class LoginActivity extends BaseActivity<LoginPresenter, LoginBinding> implements LoginView {

    @Override
    public int getLayoutId() {
        return R.layout.login;
    }

    @Override
    public void initView() {

    }

    public void goToWelcome(View view) {
        TRouter.go(Jumpter.HOME);
    }

    @Override
    public void setLoginContent(String content) {
        mViewBinding.btn.setText(content);
    }
}
