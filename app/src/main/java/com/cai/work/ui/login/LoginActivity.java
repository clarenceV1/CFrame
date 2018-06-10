package com.cai.work.ui.login;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.cai.framework.base.GodBasePresenter;
import com.cai.work.R;
import com.cai.work.base.AppBaseActivity;
import com.cai.work.dagger.component.DaggerAppComponent;
import com.cai.work.databinding.LoginBinding;
import com.cai.work.databinding.MainBinding;
import com.cai.work.ui.main.MainView;

import java.util.List;

import javax.inject.Inject;

@Route(path = "/AppModule/LoginActivity", name = "登录")
public class LoginActivity extends AppBaseActivity<LoginBinding> implements LoginView {
    @Inject
    LoginPresenter presenter;

    @Override
    public void initDagger() {
        DaggerAppComponent.create().inject(this);
    }

    @Override
    public void addPresenters(List<GodBasePresenter> observerList) {
        observerList.add(presenter);
    }

    @Override
    public void initView() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.login;
    }
}
