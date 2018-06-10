package com.cai.work.ui.login;

import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.CompoundButton;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.cai.framework.base.GodBasePresenter;
import com.cai.work.R;
import com.cai.work.base.AppBaseActivity;
import com.cai.work.dagger.component.DaggerAppComponent;
import com.cai.work.databinding.LoginBinding;

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
        mViewBinding.commonHeadView.ivGoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mViewBinding.commonHeadView.tvTitle.setText(getString(R.string.login_titile));
        mViewBinding.showPassword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mViewBinding.editPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    mViewBinding.editPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });
        mViewBinding.showPassword.setChecked(false);
        mViewBinding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String account = mViewBinding.editMobile.getText().toString();
                String password = mViewBinding.editPassword.getText().toString();
                boolean isSavePassword = mViewBinding.cbSavePassword.isChecked();
                presenter.requestLogin(account, password,isSavePassword);
            }
        });
        mViewBinding.tvForgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.login;
    }
}
