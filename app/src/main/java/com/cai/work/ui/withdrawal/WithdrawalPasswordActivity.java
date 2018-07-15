package com.cai.work.ui.withdrawal;

import android.text.TextUtils;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.cai.framework.base.GodBasePresenter;
import com.cai.framework.utils.SMSCountDownTimer;
import com.cai.work.R;
import com.cai.work.base.App;
import com.cai.work.base.AppBaseActivity;
import com.cai.work.databinding.WithdrawalPasswordBinding;
import com.example.clarence.utillibrary.KeyBoardUtils;
import com.example.clarence.utillibrary.StringUtils;
import com.example.clarence.utillibrary.ToastUtils;

import java.util.List;

import javax.inject.Inject;

@Route(path = "/AppModule/WithdrawalPasswordActivity", name = "设置提现密码")
public class WithdrawalPasswordActivity extends AppBaseActivity<WithdrawalPasswordBinding> implements WithdrawalPasswordView {

    @Inject
    WithdrawalPasswordPresenter presenter;
    SMSCountDownTimer countDownTimer;
    String mobile;

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
        mViewBinding.commonHeadView.tvTitle.setText(getString(R.string.withdrawal_password_titile));
        mViewBinding.commonHeadView.ivGoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mViewBinding.tvIdentifyCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countDownTimer = new SMSCountDownTimer(mViewBinding.tvIdentifyCode, 60000, 1000);
                countDownTimer.start();
                presenter.requestIdentifyCode(mobile);
            }
        });

        mViewBinding.btnCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sms = mViewBinding.editCode.getText().toString();
                if (TextUtils.isEmpty(sms)) {
                    ToastUtils.showShort(getString(R.string.register_identify_hint));
                    mViewBinding.editCode.requestFocus();
                    KeyBoardUtils.forceShow(mViewBinding.editCode);
                    return;
                }

                String oldPassword = "";//mViewBinding.editOldPassword.getText().toString();
//                if (TextUtils.isEmpty(oldPassword)) {
//                    ToastUtils.showShort(getString(R.string.register_old_password_hint));
//                    mViewBinding.editOldPassword.requestFocus();
//                    KeyBoardUtils.forceShow(mViewBinding.editOldPassword);
//                    return;
//                }

                String newPassword = mViewBinding.editNewPassword.getText().toString();
                if (TextUtils.isEmpty(newPassword)) {
                    ToastUtils.showShort(getString(R.string.register_new_password_hint));
                    mViewBinding.editNewPassword.requestFocus();
                    KeyBoardUtils.forceShow(mViewBinding.editNewPassword);
                    return;
                }
                String confirmNewPassword = mViewBinding.editCommitNewPassword.getText().toString();
                if (TextUtils.isEmpty(confirmNewPassword)) {
                    ToastUtils.showShort(getString(R.string.register_new_password_commit_hint));
                    mViewBinding.editCommitNewPassword.requestFocus();
                    KeyBoardUtils.forceShow(mViewBinding.editCommitNewPassword);
                    return;
                }
                if (!newPassword.equals(confirmNewPassword)) {
                    ToastUtils.showShort(getString(R.string.register_2_password_toast));
                    return;
                }
                presenter.resetWithdrawalPassword(sms, oldPassword, newPassword);
            }
        });
        presenter.getMobile();
    }

    @Override
    public int getLayoutId() {
        return R.layout.withdrawal_password;
    }

    @Override
    public void getMobile(String mobile) {
        this.mobile = mobile;
        mViewBinding.tvMobile.setText(StringUtils.encryptMobile(mobile));
    }

    @Override
    public void toast(int type, String msg) {
        ToastUtils.showShort(msg);
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
