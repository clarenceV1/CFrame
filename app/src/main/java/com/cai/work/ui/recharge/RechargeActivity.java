package com.cai.work.ui.recharge;

import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.cai.framework.base.GodBasePresenter;
import com.cai.lib.logger.Logger;
import com.cai.work.R;
import com.cai.work.base.AppBaseActivity;
import com.cai.work.dagger.component.DaggerAppComponent;
import com.cai.work.databinding.RechargeBinding;
import com.cai.work.ui.rank.RankPresenter;

import java.util.List;

import javax.inject.Inject;

@Route(path = "/AppModule/RechargeActivity", name = "充值")
public class RechargeActivity extends AppBaseActivity<RechargeBinding> implements RechargeView {
    @Inject
    RankPresenter presenter;
    int choosePosition = 0;

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
        mViewBinding.commonHeadView.tvTitle.setText(getString(R.string.recharge_title));
        mViewBinding.commonHeadView.ivGoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mViewBinding.checkboxAli.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    choosePosition = 0;
                    Logger.d("onCheckedChanged:111===>" + choosePosition);
                }
            }
        });
        mViewBinding.checkboxWechat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    choosePosition = 1;
                    Logger.d("onCheckedChanged:222===>" + choosePosition);
                }
            }
        });
        mViewBinding.checkboxUnderLine.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    choosePosition = 2;
                    Logger.d("onCheckedChanged:333===>" + choosePosition);
                }
            }
        });
        mViewBinding.btnCommitPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (choosePosition) {
                    case 0:
                        ARouter.getInstance().build("/AppModule/WebActivity").withInt("paymentWay", 2).navigation();
                        break;
                    case 1:
                        ARouter.getInstance().build("/AppModule/WebActivity").withInt("paymentWay", 1).navigation();
                        break;
                    case 2:
                        ARouter.getInstance().build("/AppModule/RechargeUnderLine").navigation();
                        break;
                }
            }
        });
        mViewBinding.checkboxAli.setChecked(true);
    }

    @Override
    public int getLayoutId() {
        return R.layout.recharge;
    }
}
