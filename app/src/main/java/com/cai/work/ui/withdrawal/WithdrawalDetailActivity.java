package com.cai.work.ui.withdrawal;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.cai.framework.base.GodBasePresenter;
import com.cai.work.R;
import com.cai.work.base.AppBaseActivity;
import com.cai.work.databinding.WithdrawalBinding;

import java.util.List;

import javax.inject.Inject;

@Route(path = "/AppModule/WithdrawalDetailActivity", name = "提现明细")
public class WithdrawalDetailActivity extends AppBaseActivity<WithdrawalBinding> implements WithdrawalDetailView {

    @Inject
    WithdrawalDetailPresenter presenter;

    @Override
    public void initDagger() {

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
        return R.layout.withdrawal_detail;
    }
}
