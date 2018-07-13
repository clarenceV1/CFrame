package com.cai.work.ui.debug;

import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.cai.framework.base.GodBasePresenter;
import com.cai.work.R;
import com.cai.work.base.App;
import com.cai.work.base.AppBaseActivity;
import com.cai.work.databinding.DebugBinding;

import java.util.List;

import javax.inject.Inject;

@Route(path = "/MeetOne/DebugActivity", name = "调试页面")
public class DebugActivity extends AppBaseActivity<DebugBinding> implements DebugView {

    @Inject
    DebugPresenter presenter;

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
        long userId = presenter.getUserId();
        if (userId == 0) {
            mViewBinding.tvLine1.setText("未登陆");
        } else {
            mViewBinding.tvLine1.setText("userId:" + userId);
        }


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

    @Override
    public int getLayoutId() {
        return R.layout.debug;
    }
}
