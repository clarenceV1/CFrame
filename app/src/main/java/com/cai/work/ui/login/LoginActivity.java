package com.cai.work.ui.login;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.cai.framework.base.GodBasePresenter;
import com.cai.framework.utils.LanguageLocalUtil;
import com.cai.work.R;
import com.cai.work.base.App;
import com.cai.work.base.AppBaseActivity;
import com.cai.work.bean.PhoneCode;
import com.cai.work.bean.User;
import com.cai.work.databinding.LoginBinding;
import com.example.clarence.utillibrary.ToastUtils;

import java.util.List;

import javax.inject.Inject;

@Route(path = "/MeetOne/LoginActivity", name = "登录")
public class LoginActivity extends AppBaseActivity<LoginBinding> implements LoginView, View.OnClickListener {

    @Inject
    LoginPresenter presenter;
    @Autowired(name = "isRegister")
    boolean isRegister;
    private String nation_code;
    private CountDownTimer mCountDownTimer;
    private boolean isTimeDown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ARouter.getInstance().inject(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getLayoutId() {
        return R.layout.login;
    }

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
        initCode();
        reset();
        initListener();
    }

    private void initCode() {
        if (LanguageLocalUtil.isChinese()) {
            nation_code = "86";
        } else {
            nation_code = "1";
        }
        mViewBinding.tvNationCode.setText("+" + nation_code);
    }

    private void initListener() {
        mViewBinding.tvNationCode.setOnClickListener(this);
        mViewBinding.tvGetCode.setOnClickListener(this);
        mViewBinding.tvCommit.setOnClickListener(this);
        mViewBinding.titleBar.setRightClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isRegister = !isRegister;
                reset();
            }
        });
        mViewBinding.titleBar.setLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void reset() {
        if (isRegister) {
            mViewBinding.tvCommit.setText(R.string.register);
            mViewBinding.titleBar.setTitleText(getString(R.string.register));
            mViewBinding.titleBar.setRightText(getString(R.string.login));
        } else {
            mViewBinding.tvCommit.setText(R.string.login);
            mViewBinding.titleBar.setTitleText(getString(R.string.login));
            mViewBinding.titleBar.setRightText(getString(R.string.register));
        }
        mViewBinding.tvGetCode.setText(R.string.phone_code_get);
        isTimeDown = false;
        if (mCountDownTimer != null) {
            mCountDownTimer.cancel();
            mCountDownTimer = null;
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.tvNationCode://选择国家
                ARouter.getInstance().build("/MeetOne/NationCodeActivity").navigation();
//                NationCodeActivity.entryActivityForResult(this, 100, RESULT_KEY);
                break;
            case R.id.tvGetCode:
                if (!isTimeDown) {
                    String phoneNum = mViewBinding.editPhone.getText().toString();
                    if (TextUtils.isEmpty(phoneNum.trim())) {
                        ToastUtils.showShort(R.string.phone_null);
                        return;
                    }
                    presenter.getPhoneCode(nation_code, phoneNum);
                }
                break;
            case R.id.tvCommit:
                String phoneNum = mViewBinding.editPhone.getText().toString();
                String code = mViewBinding.editCode.getText().toString();
                presenter.loginOrRegister(nation_code, phoneNum, code);
                break;
        }
    }

    private void downTime(int time) {
        isTimeDown = true;
        mCountDownTimer = new CountDownTimer(time * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mViewBinding.tvGetCode.setTextColor(getResources().getColor(R.color.gray_757575));
                mViewBinding.tvGetCode.setText(getString(R.string.phone_code_resend) + "(" + millisUntilFinished / 1000 + ")");
            }

            @Override
            public void onFinish() {
                isTimeDown = false;
                mViewBinding.tvGetCode.setTextColor(getResources().getColor(R.color.blue_8D48FF));
                mViewBinding.tvGetCode.setText(R.string.phone_code_resend);
                mCountDownTimer = null;
            }
        }.start();
    }

    @Override
    protected void onDestroy() {
        if (mCountDownTimer != null) {
            mCountDownTimer.cancel();
            mCountDownTimer = null;
        }
        super.onDestroy();
    }

    @Override
    public void callBack(PhoneCode data) {
        downTime(data.getTimer());
    }

    @Override
    public void callBack(String message) {
        ToastUtils.showShort(message);
    }

    @Override
    public void callBack(User data) {
        finish();
    }

}
