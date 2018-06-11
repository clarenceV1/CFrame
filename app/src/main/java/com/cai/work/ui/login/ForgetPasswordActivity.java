package com.cai.work.ui.login;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.cai.framework.base.GodBasePresenter;
import com.cai.work.R;
import com.cai.work.base.AppBaseActivity;
import com.cai.work.dagger.component.DaggerAppComponent;
import com.cai.work.databinding.ForgetPasswordBinding;

import java.util.List;

import javax.inject.Inject;
@Route(path = "/AppModule/ForgetPasswordActivity", name = "忘记密码")
public class ForgetPasswordActivity extends AppBaseActivity<ForgetPasswordBinding> implements ForgetPasswordView {

    @Inject
    ForgetPasswordPresenter passwordPresenter;

    @Override
    public void initDagger() {
        DaggerAppComponent.create().inject(this);
    }

    @Override
    public void addPresenters(List<GodBasePresenter> observerList) {
        observerList.add(passwordPresenter);
    }

    @Override
    public void initView() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.forget_password;
    }
}
