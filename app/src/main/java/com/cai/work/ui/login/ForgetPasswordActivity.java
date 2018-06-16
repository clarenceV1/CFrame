package com.cai.work.ui.login;

import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.CompoundButton;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.cai.framework.base.GodBasePresenter;
import com.cai.framework.utils.SMSCountDownTimer;
import com.cai.work.R;
import com.cai.work.base.AppBaseActivity;
import com.cai.work.dagger.component.DaggerAppComponent;
import com.cai.work.databinding.ForgetPasswordBinding;
import com.example.clarence.utillibrary.KeyBoardUtils;
import com.example.clarence.utillibrary.MobileUtils;
import com.example.clarence.utillibrary.ToastUtils;

import java.util.List;

import javax.inject.Inject;

@Route(path = "/AppModule/ForgetPasswordActivity", name = "忘记密码")
public class ForgetPasswordActivity extends AppBaseActivity<ForgetPasswordBinding> implements ForgetPasswordView {

    @Inject
    ForgetPasswordPresenter presenter;
    SMSCountDownTimer countDownTimer;

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
        mViewBinding.commonHeadView.tvTitle.setText(getString(R.string.forget_password_titile));
        mViewBinding.tvGetIdentify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mobile = mViewBinding.editMobile.getText().toString();
                if (TextUtils.isEmpty(mobile)) {
                    ToastUtils.showShort(getString(R.string.login_mobile_hint));
                    mViewBinding.editMobile.requestFocus();
                    KeyBoardUtils.forceShow(mViewBinding.editMobile);
                    return;
                }
                countDownTimer = new SMSCountDownTimer(mViewBinding.tvGetIdentify, 60000, 1000);
                countDownTimer.start();
                presenter.requestIdentifyCode(mobile);
            }
        });
        mViewBinding.imgShowPs.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mViewBinding.editPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    mViewBinding.editPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });
        mViewBinding.imgShowPs.setChecked(false);
        mViewBinding.imgConfirmShowPs.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mViewBinding.editConfirmPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    mViewBinding.editConfirmPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });
        mViewBinding.imgConfirmShowPs.setChecked(false);
        mViewBinding.btnRestPs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mobile = mViewBinding.editMobile.getText().toString();
                if (TextUtils.isEmpty(mobile)) {
                    ToastUtils.showShort(getString(R.string.login_mobile_hint));
                    mViewBinding.editMobile.requestFocus();
                    KeyBoardUtils.forceShow(mViewBinding.editMobile);
                    return;
                } else if (!MobileUtils.isChinaPhoneLegal(mobile)) {
                    ToastUtils.showShort(getString(R.string.register_mobile_err_toast));
                    return;
                }
                String sms = mViewBinding.editIdentify.getText().toString();
                if (TextUtils.isEmpty(sms)) {
                    ToastUtils.showShort(getString(R.string.register_identify_hint));
                    mViewBinding.editIdentify.requestFocus();
                    KeyBoardUtils.forceShow(mViewBinding.editIdentify);
                    return;
                }
                String loginPassword = mViewBinding.editPassword.getText().toString();
                if (TextUtils.isEmpty(loginPassword)) {
                    ToastUtils.showShort(getString(R.string.register_password_hint));
                    mViewBinding.editPassword.requestFocus();
                    KeyBoardUtils.forceShow(mViewBinding.editPassword);
                    return;
                }
                String confirmPs = mViewBinding.editConfirmPassword.getText().toString();
                if (TextUtils.isEmpty(confirmPs)) {
                    ToastUtils.showShort(getString(R.string.register_no_confirm_password_toast));
                    mViewBinding.editConfirmPassword.requestFocus();
                    KeyBoardUtils.forceShow(mViewBinding.editConfirmPassword);
                    return;
                }
                if (!loginPassword.equals(confirmPs)) {
                    ToastUtils.showShort(getString(R.string.register_2_password_toast));
                    return;
                }
                presenter.forgetPassword(mobile, sms, loginPassword);
            }
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.forget_password;
    }

    @Override
    public void toast(int type, String msg) {
        ToastUtils.showShort(msg);
        if (type == 4) {
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        clearTimer();
    }

    private void clearTimer() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
            countDownTimer = null;
        }
    }
}
