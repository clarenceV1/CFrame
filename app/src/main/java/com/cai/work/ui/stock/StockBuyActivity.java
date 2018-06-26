package com.cai.work.ui.stock;

import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.cai.framework.base.GodBasePresenter;
import com.cai.work.R;
import com.cai.work.base.AppBaseActivity;
import com.cai.work.dagger.component.DaggerAppComponent;
import com.cai.work.databinding.StockBuyBinding;

import java.util.List;

import javax.inject.Inject;

@Route(path = "/AppModule/StockBuyActivity", name = "股票买卖页面")
public class StockBuyActivity extends AppBaseActivity<StockBuyBinding> implements StockBuyView {
    @Inject
    StockBuyPresenter presenter;

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
        mViewBinding.commonHeadView.tvTitle.setText(getString(R.string.stock_buy_titile));
        mViewBinding.commonHeadView.ivGoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.stock_buy;
    }
}
