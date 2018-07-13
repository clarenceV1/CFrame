package com.cai.work.ui.debug;

import android.app.ActivityManager;
import android.content.Context;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.cai.framework.base.GodActivityManger;
import com.cai.framework.base.GodBasePresenter;
import com.cai.work.R;
import com.cai.work.base.App;
import com.cai.work.base.AppBaseActivity;
import com.cai.work.common.Constant;
import com.cai.work.databinding.DebugBinding;
import com.example.clarence.utillibrary.ToastUtils;

import java.util.List;

import javax.inject.Inject;

@Route(path = "/MeetOne/DebugActivity", name = "调试页面")
public class DebugActivity extends AppBaseActivity<DebugBinding> implements DebugView {

    @Inject
    DebugPresenter presenter;
    boolean isTestEnv;

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
        initTitle();
        initData();
    }

    private void initData() {
        initUserInfo();
        initEnv();

        if (isTestEnv) {
            mViewBinding.edit.setText(Constant.TEST_BASE_URL);
        } else {
            mViewBinding.edit.setText(Constant.OFFICIAL_BASE_URL);
        }
    }

    private void initUserInfo() {
        long userId = presenter.getUserId();
        if (userId == 0) {
            mViewBinding.tvLine1.setText("未登陆");
        } else {
            mViewBinding.tvLine1.setText("userId:" + userId);
        }
    }

    private void initEnv() {
        isTestEnv = Constant.isTestEnv();
        if (isTestEnv) {
            mViewBinding.tvLine2.setText("当前是测试环境");
        } else {
            mViewBinding.tvLine2.setText("当前是正式环境");
        }
        mViewBinding.tvLine2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Constant.saveTestEnv(!isTestEnv);
                GodActivityManger.getInstance().exitApp();
            }
        });
    }

    private void initTitle() {
        mViewBinding.titleBar.setTitleText(getString(R.string.message));
        mViewBinding.titleBar.setLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void btnSearch(View view) {
        String url = mViewBinding.edit.getText().toString();
        ARouter.getInstance().build("/MeetOne/WebActivity")
                .withCharSequence("url", url)
                .withCharSequence("title", "我是测试页面")
                .navigation();
    }

    @Override
    public int getLayoutId() {
        return R.layout.debug;
    }
}
