package com.cai.work.ui;

import android.arch.lifecycle.LifecycleRegistry;
import android.view.View;
import android.widget.Toast;

import com.cai.annotation.apt.Router;
import com.cai.framework.base.BaseLifecycleObserver;
import com.cai.work.R;
import com.cai.work.base.BaseActivity;
import com.cai.work.base.Jumpter;
import com.cai.work.databinding.LoginBinding;
import com.cai.work.ui.lifecycleobserver.LoginObserver;
import com.cai.work.ui.presenter.LoginPresenter;
import com.cai.work.ui.presenter.LoginView;

import java.util.Map;

@Router(Jumpter.LOGIN)
public class LoginActivity extends BaseActivity<LoginPresenter, LoginBinding, LoginObserver> implements LoginView {

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

    @Override
    public LoginObserver getLifecycleObserver(LifecycleRegistry mRegistry, Map data) {
        return new LoginObserver(this, mRegistry, data);
    }
}
