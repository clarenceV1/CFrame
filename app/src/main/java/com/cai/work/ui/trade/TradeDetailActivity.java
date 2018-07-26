package com.cai.work.ui.trade;

import com.cai.framework.base.GodBasePresenter;
import com.cai.work.R;
import com.cai.work.base.App;
import com.cai.work.base.AppBaseActivity;
import com.cai.work.databinding.TradeDetailBinding;

import java.util.List;

import javax.inject.Inject;

public class TradeDetailActivity extends AppBaseActivity<TradeDetailBinding> implements TradeDetailView {
    @Inject
    TradeDetailPresenter presenter;

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

    }

    @Override
    public int getLayoutId() {
        return R.layout.trade_detail;
    }
}
