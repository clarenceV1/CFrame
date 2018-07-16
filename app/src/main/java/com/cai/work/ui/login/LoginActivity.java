package com.cai.work.ui.login;

import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.CompoundButton;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.facade.callback.NavCallback;
import com.alibaba.android.arouter.launcher.ARouter;
import com.cai.framework.base.GodBaseConfig;
import com.cai.framework.base.GodBasePresenter;
import com.cai.work.R;
import com.cai.work.base.App;
import com.cai.work.base.AppBaseActivity;
import com.cai.work.dagger.component.DaggerAppComponent;
import com.cai.work.databinding.LoginBinding;
import com.example.clarence.utillibrary.ToastUtils;

import java.util.List;

import javax.inject.Inject;

@Route(path = "/AppModule/LoginActivity", name = "登录")
public class LoginActivity extends AppBaseActivity<LoginBinding> implements LoginView {
    @Inject
    LoginPresenter presenter;

    @Override
    public void initDagger() {
        App.getAppComponent().inject(this);
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

        mViewBinding.llForgetPwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewBinding.cbSavePassword.setChecked(!mViewBinding.cbSavePassword.isChecked());
            }
        });

        mViewBinding.showPassword.setChecked(false);
        mViewBinding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String account = mViewBinding.editMobile.getText().toString();
                String password = mViewBinding.editPassword.getText().toString();
                boolean isSavePassword = mViewBinding.cbSavePassword.isChecked();
                presenter.requestLogin(account, password, isSavePassword);
            }
        });
        mViewBinding.tvForgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance().build("/AppModule/ForgetPasswordActivity").navigation();
            }
        });
        mViewBinding.tvRegiser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance().build("/AppModule/RegisterActivity").navigation();
            }
        });
//        if (GodBaseConfig.getInstance().isDebug()) {
//            mViewBinding.editMobile.setText("13276967598");
//            mViewBinding.editPassword.setText("123456");
//        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.login;
    }

    @Override
    public void loginSuccess() {
        ARouter.getInstance().build("/AppModule/MainActivity").withInt("position", 1).navigation(this, new NavCallback() {
            @Override
            public void onArrival(Postcard postcard) {
                ToastUtils.showShort(getResources().getString(R.string.login_success));
                finish();
            }
        });
    }

    @Override
    public void toast(int type, String msg) {
        ToastUtils.showShort(msg);
    }
}
