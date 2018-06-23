package com.cai.work.ui.news;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.alibaba.fastjson.JSON;
import com.cai.framework.base.GodBasePresenter;
import com.cai.work.R;
import com.cai.work.base.AppBaseActivity;
import com.cai.work.bean.ForwardAccount;
import com.cai.work.bean.StockAccount;
import com.cai.work.databinding.AccountsDetailBinding;

import java.util.List;

@Route(path = "/AppModule/AccountsDetailActivity", name = "结算详情")
public class AccountsDetailActivity extends AppBaseActivity<AccountsDetailBinding> {

    @Autowired(name = "jsonStr")
    String forwardAccountJson;

    @Autowired(name = "type")
    int type;//1：stock 2:forword

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ARouter.getInstance().inject(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initDagger() {
    }

    @Override
    public void addPresenters(List<GodBasePresenter> observerList) {
    }

    @Override
    public int getLayoutId() {
        return R.layout.accounts_detail;
    }

    @Override
    public void initView() {
        mViewBinding.commonHeadView.ivGoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mViewBinding.commonHeadView.tvTitle.setText(getString(R.string.account_detail_title));


        if (!TextUtils.isEmpty(forwardAccountJson)) {
            if (type == 2) {
                ForwardAccount forwardAccount = JSON.parseObject(forwardAccountJson, ForwardAccount.class);
                mViewBinding.tvTradeId.setText(forwardAccount.getOrderNo());
                mViewBinding.tvTradeKind.setText(forwardAccount.getContractName());
                mViewBinding.tvTradeNum.setText(forwardAccount.getBuyAmount() + "手");
                mViewBinding.tvTradePrice.setText(forwardAccount.getOpenPrice());
                mViewBinding.tvClosePrice.setText(forwardAccount.getClosePrice());
                mViewBinding.tvCloseTime.setText(forwardAccount.getCloseDealDate());
                mViewBinding.tvCloseReson.setText(forwardAccount.getApproveStateText());
                mViewBinding.tvTradeYK.setText(forwardAccount.getYkMoney());
            } else if (type == 1) {
                StockAccount stockAccount = JSON.parseObject(forwardAccountJson, StockAccount.class);
                mViewBinding.tvTradeId.setText(stockAccount.getOrderNo());
                mViewBinding.tvTradeKind.setText(stockAccount.getStockName());
                mViewBinding.tvTradeNum.setText(stockAccount.getDealAmount() + "股");
                mViewBinding.tvTradePrice.setText(stockAccount.getBuyPrice());
                mViewBinding.tvClosePrice.setText(stockAccount.getSellPrice());
                mViewBinding.tvCloseTime.setText(stockAccount.getSellDealDate());
                mViewBinding.tvCloseReson.setText(stockAccount.getApproveStateText());
                mViewBinding.tvTradeYK.setText(stockAccount.getYkMoney());
            }

        }
    }
}
