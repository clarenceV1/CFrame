package com.cai.work.ui.forward;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.cai.framework.base.GodBasePresenter;
import com.cai.work.R;
import com.cai.work.base.App;
import com.cai.work.base.AppBaseActivity;
import com.cai.work.bean.ForwardBuy;
import com.cai.work.bean.StockBuyMoney;
import com.cai.work.databinding.ForwardBuyBinding;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

@Route(path = "/AppModule/ForwardDetailActivity", name = "期货购买页面")
public class ForwardBuyActivity extends AppBaseActivity<ForwardBuyBinding> implements ForwardBuyView {

    @Autowired(name = "type")
    int type = 1;//1涨，2跌
    @Autowired(name = "forwardCode")
    String forwardCode;
    @Inject
    ForwardBuyPresenter presenter;
    ForwardBuy data;
    ForwardBuyMoneyAdapter amountAdapter;
    ForwardBuyMoneyAdapter boundAdapter;
    ForwardBuyMoneyAdapter zyAdapter;
    ForwardBuyMoneyAdapter zsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ARouter.getInstance().inject(this);
        super.onCreate(savedInstanceState);
    }

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
        View rootView = findViewById(android.R.id.content);
        if (rootView != null) {
            rootView.setBackgroundResource(R.color.ys_24_24_24);
        }
        if (type == 1) {
            mViewBinding.commonHeadView.tvTitle.setText("买涨");
        } else {
            mViewBinding.commonHeadView.tvTitle.setText("买跌");
        }
        mViewBinding.commonHeadView.ivGoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        freshViewColor();
        mViewBinding.tvRecharge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance().build("/AppModule/RechargeActivity").navigation();
            }
        });

        amountAdapter = new ForwardBuyMoneyAdapter(this, type);
        mViewBinding.gridView.setAdapter(amountAdapter);
        mViewBinding.gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                amountAdapter.setCheckPosition(position);
            }
        });

        boundAdapter = new ForwardBuyMoneyAdapter(this, type);
        mViewBinding.gridViewBond.setAdapter(boundAdapter);
        mViewBinding.gridViewBond.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                boundAdapter.setCheckPosition(position);
            }
        });

        zyAdapter = new ForwardBuyMoneyAdapter(this, type);
        mViewBinding.gridViewZy.setAdapter(zyAdapter);
        mViewBinding.gridViewZy.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                zyAdapter.setCheckPosition(position);
            }
        });

        zsAdapter = new ForwardBuyMoneyAdapter(this, type);
        mViewBinding.gridViewZs.setAdapter(zsAdapter);
        mViewBinding.gridViewZs.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                zsAdapter.setCheckPosition(position);
            }
        });

        presenter.requestForwardBuy(forwardCode);

    }

    private void freshViewColor() {
        if (type == 1) {
            mViewBinding.tvMoney.setTextColor(getResources().getColor(R.color.ys_e6241a));
            mViewBinding.tvHoldTime.setTextColor(getResources().getColor(R.color.ys_e6241a));
            mViewBinding.tvTradeMoney.setTextColor(getResources().getColor(R.color.ys_e6241a));
            mViewBinding.tvTotalMoney.setTextColor(getResources().getColor(R.color.ys_e6241a));
            mViewBinding.tvRedBag.setTextColor(getResources().getColor(R.color.ys_e6241a));
            mViewBinding.btnCommit.setBackgroundColor(getResources().getColor(R.color.ys_251_38_45));
        } else {
            mViewBinding.tvMoney.setTextColor(getResources().getColor(R.color.ys_0_154_68));
            mViewBinding.tvHoldTime.setTextColor(getResources().getColor(R.color.ys_0_154_68));
            mViewBinding.tvTradeMoney.setTextColor(getResources().getColor(R.color.ys_0_154_68));
            mViewBinding.tvTotalMoney.setTextColor(getResources().getColor(R.color.ys_0_154_68));
            mViewBinding.tvRedBag.setTextColor(getResources().getColor(R.color.ys_0_154_68));
            mViewBinding.btnCommit.setBackgroundColor(getResources().getColor(R.color.ys_0_229_0));
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.forward_buy;
    }

    @Override
    public void callBack(ForwardBuy data) {
        this.data = data;
        mViewBinding.tvForwardName.setText(data.getName());
        mViewBinding.tvForwardCode.setText(data.getCode());
        mViewBinding.tvMoney.setText(data.getBalance());
        mViewBinding.tvTradeMoney.setText(data.getCost());

        String[] amounts = data.getAmount();
        if (amounts != null && amounts.length > 0) {
            List<StockBuyMoney> buyMoneyList = new ArrayList<>();
            for (String amount : amounts) {
                StockBuyMoney money = new StockBuyMoney();
                money.setTxt(amount);
                money.setType(1);
                buyMoneyList.add(money);
            }
            amountAdapter.update(buyMoneyList);
        }

        String[] bonds = data.getBond();
        if (bonds != null && bonds.length > 0) {
            List<StockBuyMoney> buyMoneyList = new ArrayList<>();
            for (String bond : bonds) {
                StockBuyMoney money = new StockBuyMoney();
                money.setTxt(bond);
                money.setType(2);
                buyMoneyList.add(money);
            }
            boundAdapter.update(buyMoneyList);
        }

        String[] zys = data.getZy();
        if (zys != null && zys.length > 0) {
            List<StockBuyMoney> buyMoneyList = new ArrayList<>();
            for (String zy : zys) {
                StockBuyMoney money = new StockBuyMoney();
                money.setTxt(zy);
                money.setType(3);
                buyMoneyList.add(money);
            }
            zyAdapter.update(buyMoneyList);
        }

        String[] zss = data.getZs();
        if (zss != null && zss.length > 0) {
            List<StockBuyMoney> buyMoneyList = new ArrayList<>();
            for (String zs : zss) {
                StockBuyMoney money = new StockBuyMoney();
                money.setTxt(zs);
                money.setType(4);
                buyMoneyList.add(money);
            }
            zsAdapter.update(buyMoneyList);
        }
    }

    @Override
    public void toast(String msg) {

    }
}
