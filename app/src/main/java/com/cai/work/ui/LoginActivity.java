package com.cai.work.ui;

import android.view.View;

import com.cai.annotation.apt.Router;
import com.cai.annotation.aspect.CostTime;
import com.cai.apt.TRouter;
import com.cai.framework.base.BaseActivity;
import com.cai.work.R;
import com.cai.work.base.Jumpter;
import com.cai.work.bean.Animal;
import com.cai.work.databinding.LoginBinding;
import com.cai.work.databinding.MainBinding;
import com.cai.work.ui.presenter.LoginPresenter;
import com.cai.work.ui.presenter.LoginView;
import com.cai.work.ui.presenter.MainPresenter;
import com.cai.work.ui.presenter.MainView;
import com.cai.work.utils.InstanceUtil;

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
