package com.cai.work.ui.recharge;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.cai.framework.base.GodBasePresenter;
import com.cai.work.R;
import com.cai.work.base.AppBaseActivity;
import com.cai.work.dagger.component.DaggerAppComponent;
import com.cai.work.databinding.RankBinding;
import com.cai.work.ui.rank.RankPresenter;
import com.cai.work.ui.rank.RankView;

import java.util.List;

import javax.inject.Inject;

@Route(path = "/AppModule/RechargeActivity", name = "充值")
public class RechargeActivity extends AppBaseActivity<RankBinding> implements RechargeView {
    @Inject
    RankPresenter presenter;

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
        return R.layout.recharge;
    }
}
