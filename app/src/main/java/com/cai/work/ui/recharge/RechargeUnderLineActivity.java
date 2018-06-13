package com.cai.work.ui.recharge;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.cai.framework.base.GodBasePresenter;
import com.cai.work.R;
import com.cai.work.base.AppBaseActivity;
import com.cai.work.dagger.component.DaggerAppComponent;
import com.cai.work.databinding.RechargeBinding;

import java.util.List;

import javax.inject.Inject;

@Route(path = "/AppModule/RechargeUnderLine", name = "线下充值")
public class RechargeUnderLineActivity extends AppBaseActivity<RechargeBinding> implements RechargeUnderLineView {

    @Inject
    RechargeUnderLinePresenter presenter;

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
        return R.layout.recharge_underline;
    }
}
