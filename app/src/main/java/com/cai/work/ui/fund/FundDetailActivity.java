package com.cai.work.ui.fund;

import com.cai.framework.base.GodBasePresenter;
import com.cai.work.R;
import com.cai.work.base.AppBaseActivity;
import com.cai.work.bean.FundDetail;
import com.cai.work.dagger.component.DaggerAppComponent;
import com.cai.work.databinding.FundDetailBinding;

import java.util.List;

import javax.inject.Inject;

public class FundDetailActivity extends AppBaseActivity<FundDetailBinding> implements FundDetailView {

    @Inject
    FundDetailPresenter presenter;

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

    }

    @Override
    public int getLayoutId() {
        return R.layout.fund_detail;
    }

    @Override
    public void getData(FundDetail data) {

    }
}
