package com.cai.work.ui;

import android.view.View;
import android.widget.Toast;

import com.cai.annotation.apt.Router;
import com.cai.work.R;
import com.cai.work.base.AppBaseActivity;
import com.cai.work.base.Jumpter;
import com.cai.work.databinding.LoginBinding;
import com.cai.work.ui.lifecycleobserver.LoginObserver;
import com.cai.work.ui.presenter.LoginPresenter;
import com.cai.work.ui.presenter.LoginView;

@Router(Jumpter.LOGIN)
public class LoginActivity extends AppBaseActivity<LoginObserver, LoginPresenter, LoginBinding> implements LoginView {

    @Override
    public int getLayoutId() {
        return R.layout.login;
    }

    @Override
    public void initView() {

    }

    public void goToLogin(View view) {
        mPresenter.login();
        Toast.makeText(this, "去登录", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setLoginContent(String content) {
        mViewBinding.btn.setText(content);
    }
}
