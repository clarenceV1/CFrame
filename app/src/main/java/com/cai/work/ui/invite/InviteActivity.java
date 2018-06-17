package com.cai.work.ui.invite;

import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.cai.framework.base.GodBasePresenter;
import com.cai.work.R;
import com.cai.work.base.AppBaseActivity;
import com.cai.work.dagger.component.DaggerAppComponent;
import com.cai.work.databinding.InviteBinding;
import com.example.clarence.utillibrary.ClipBoardUtils;
import com.example.clarence.utillibrary.ToastUtils;

import java.util.List;

import javax.inject.Inject;

@Route(path = "/AppModule/InviteActivity", name = "邀请中心")
public class InviteActivity extends AppBaseActivity<InviteBinding> implements InviteView {

    @Inject
    InvitePresenter presenter;

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
        mViewBinding.commonHeadView.tvTitle.setText(getString(R.string.invite_titile));
        mViewBinding.commonHeadView.tvRight.setText(getString(R.string.invite_rule));
        mViewBinding.commonHeadView.tvRight.setVisibility(View.VISIBLE);

        mViewBinding.tvRebateCash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        mViewBinding.tvCopyLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipBoardUtils.copyToClipBoard(InviteActivity.this, "链接", "htpp:www.baidu.com");
//                ToastUtils.showShort(getString(R.string.invite_link_toast));
            }
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.invite;
    }

}
