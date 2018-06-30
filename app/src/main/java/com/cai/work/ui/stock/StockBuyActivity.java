package com.cai.work.ui.stock;

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
import com.cai.work.bean.StockBuy;
import com.cai.work.bean.StockBuyMoney;
import com.cai.work.databinding.StockBuyBinding;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

@Route(path = "/AppModule/StockBuyActivity", name = "股票买卖页面")
public class StockBuyActivity extends AppBaseActivity<StockBuyBinding> implements StockBuyView {
    @Inject
    StockBuyPresenter presenter;
    @Autowired(name = "stockCode")
    String stockCode;
    StockBuyMoneyAdapter buyMoneyAdapter;
    StockBuyMoneyAdapter zyAdapter;
    StockBuyMoneyAdapter bondAdapter;
    StockBuyMoneyAdapter zsAdapter;
    StockBuyMoneyAdapter holdTimeAdapter;

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
        mViewBinding.commonHeadView.tvTitle.setText(getString(R.string.stock_buy_titile));
        mViewBinding.commonHeadView.ivGoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mViewBinding.commonHeadView.tvRight.setText("规则");
        mViewBinding.commonHeadView.tvRight.setVisibility(View.VISIBLE);
        mViewBinding.commonHeadView.tvRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance().build("/AppModule/WebActivity").withCharSequence("url", "http://m.hellceshi.com/tpl/app/stock_rule.html").withCharSequence("title", "规则").navigation();
            }
        });
        presenter.requestData(stockCode);

        holdTimeAdapter = new StockBuyMoneyAdapter(this);
        mViewBinding.gridViewHoldTime.setAdapter(holdTimeAdapter);
        mViewBinding.gridViewHoldTime.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                holdTimeAdapter.setCheckPosition(position);
            }
        });


        buyMoneyAdapter = new StockBuyMoneyAdapter(this);
        mViewBinding.gridView.setAdapter(buyMoneyAdapter);
        mViewBinding.gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                buyMoneyAdapter.setCheckPosition(position);
            }
        });

        bondAdapter = new StockBuyMoneyAdapter(this);
        mViewBinding.gridViewBond.setAdapter(bondAdapter);
        mViewBinding.gridViewBond.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                bondAdapter.setCheckPosition(position);
            }
        });

        zyAdapter = new StockBuyMoneyAdapter(this);
        mViewBinding.gridViewZy.setAdapter(zyAdapter);
        mViewBinding.gridViewZy.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                zyAdapter.setCheckPosition(position);
            }
        });


        zsAdapter = new StockBuyMoneyAdapter(this);
        mViewBinding.gridViewZs.setAdapter(zsAdapter);
        mViewBinding.gridViewZs.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                zsAdapter.setCheckPosition(position);
            }
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.stock_buy;
    }

    @Override
    public void callBack(StockBuy data) {
        if (data.getStock() != null) {
            mViewBinding.tvStockName.setText(data.getStock().getStockName());
            mViewBinding.tvStockCode.setText("(" + data.getStock().getStockCode() + ")");
        }
        mViewBinding.tvMoney.setText(data.getBalance());
        mViewBinding.tvCanBuyNum.setText(data.getClick_times());

        mViewBinding.tvNotice.setText("可买入1800股，资金使用率96.84%");
        mViewBinding.tvTradeMoney.setText(data.getZhf());

        mViewBinding.tvTotalMoney.setText("88888");
        mViewBinding.tvRedBag.setText("0");

        List<StockBuyMoney> holdList = new ArrayList<>();
        StockBuyMoney holdMoney = new StockBuyMoney();
        holdMoney.setMoney("¥" + data.getHoldTime());
        holdMoney.setType(0);
        holdList.add(holdMoney);
        holdTimeAdapter.update(holdList);

        float[] buyMoneys = data.getBuyMoney();
        if (buyMoneys != null && buyMoneys.length > 0) {
            List<StockBuyMoney> buyMoneyList = new ArrayList<>();
            for (float buyMoney : buyMoneys) {
                StockBuyMoney money = new StockBuyMoney();
                money.setMoney(buyMoney + "万");
                money.setType(1);
                buyMoneyList.add(money);
            }
            buyMoneyAdapter.update(buyMoneyList);
        }

        float[] zys = data.getZy();
        if (zys != null && zys.length > 0) {
            List<StockBuyMoney> zyList = new ArrayList<>();
            for (float zy : zys) {
                StockBuyMoney buyMoney = new StockBuyMoney();
                buyMoney.setType(2);
                try {
                    buyMoney.setMoney("¥" + (int) (zy * 10000));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                zyList.add(buyMoney);
            }
            zyAdapter.update(zyList);
        }

        List<StockBuyMoney> zsList = new ArrayList<>();
        StockBuyMoney buyMoney = new StockBuyMoney();
        buyMoney.setMoney("¥" + data.getZs());
        buyMoney.setType(3);
        zsList.add(buyMoney);
        zsAdapter.update(zsList);

        float[] bonds = data.getBond();
        if (bonds != null && bonds.length > 0) {
            List<StockBuyMoney> bondList = new ArrayList<>();
            for (float bond : bonds) {
                StockBuyMoney stockBuyMoney = new StockBuyMoney();
                stockBuyMoney.setMoney("¥" + bond);
                stockBuyMoney.setType(4);
                bondList.add(stockBuyMoney);
            }
            bondAdapter.update(bondList);
        }
    }

    @Override
    public void callBack(String msg) {

    }
}
