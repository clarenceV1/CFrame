package com.cai.work.ui.withdrawal;

import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.cai.framework.base.GodBasePresenter;
import com.cai.work.R;
import com.cai.work.base.AppBaseActivity;
import com.cai.work.dagger.component.DaggerAppComponent;
import com.cai.work.databinding.WithdrawalDetailBinding;

import java.util.List;

import javax.inject.Inject;

@Route(path = "/AppModule/WithdrawalDetailActivity", name = "提现明细")
public class WithdrawalDetailActivity extends AppBaseActivity<WithdrawalDetailBinding> implements WithdrawalDetailView {

    @Inject
    WithdrawalDetailPresenter presenter;

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
        mViewBinding.commonHeadView.tvTitle.setText(getString(R.string.withdrawal_detail_titile));
        mViewBinding.commonHeadView.tvRight.setText(getString(R.string.withdrawal_detail_rule));
        presenter.requestData();
    }

    @Override
    public int getLayoutId() {
        return R.layout.withdrawal_detail;
    }
}
