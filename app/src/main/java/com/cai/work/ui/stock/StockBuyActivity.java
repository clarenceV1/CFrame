package com.cai.work.ui.stock;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.alibaba.fastjson.JSON;
import com.cai.framework.base.GodBasePresenter;
import com.cai.work.R;
import com.cai.work.base.App;
import com.cai.work.base.AppBaseActivity;
import com.cai.work.bean.StockBuy;
import com.cai.work.bean.StockBuyMoney;
import com.cai.work.bean.StockBuyRedBag;
import com.cai.work.dagger.module.AppModule;
import com.cai.work.databinding.StockBuyBinding;
import com.cai.work.ui.recharge.RechargeActivity;
import com.example.clarence.utillibrary.ToastUtils;

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
    StockBuy data;
    List<StockBuyRedBag> selectRedBags;
    float redbagTotal = 0;

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
        mViewBinding.tvRecharge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance().build("/AppModule/RechargeActivity").navigation();
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
                refreshView(true);
            }
        });

        bondAdapter = new StockBuyMoneyAdapter(this);
        mViewBinding.gridViewBond.setAdapter(bondAdapter);
        mViewBinding.gridViewBond.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                bondAdapter.setCheckPosition(position);
                refreshView(false);
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
        mViewBinding.btnCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (data == null || data.getStock() == null) {
                    return;
                }
                String code = data.getStock().getStockCode();
                String name = data.getStock().getStockName();
                String marketType = data.getStock().getStockMarket();
                String price = data.getStock().getMk_price() + "";
                String amount = ((int) (buyMoneyAdapter.getBuyMoney() / data.getStock().getMk_price()) + "");
                String principal = buyMoneyAdapter.getBuyMoney() + "";
                String bzj = bondAdapter.getBuyMoney() + "";
                String zy = zyAdapter.getBuyMoney() + "";
                String zs = zsAdapter.getBuyMoney() + "";
                String redbagIds = "";
                String zhf = data.getZhf() + "";
                presenter.commitBuy(code, name, marketType, price, amount, principal, bzj, zy, zs, redbagIds, zhf);
            }
        });
        mViewBinding.tvSelectRedBag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (data != null && data.getRedBags() != null && data.getRedBags().size() > 0) {
                    ARouter.getInstance().build("/AppModule/RedPacketSelectActivity").withCharSequence("datas", JSON.toJSONString(data.getRedBags())).navigation(StockBuyActivity.this, 666);
                } else {
                    ToastUtils.showShort("您没有可用的红包！");
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (intent == null || intent.getExtras() == null) {
            return;
        }
        String result = intent.getExtras().getString("result");//得到新Activity 关闭后返回的数据
        Log.i("onActivityResult", result);
        if (!TextUtils.isEmpty(result)) {
            selectRedBags = JSON.parseArray(result, StockBuyRedBag.class);
            if (selectRedBags != null) {
                redbagTotal = 0;
                for (StockBuyRedBag selectRedBag : selectRedBags) {
                    redbagTotal += Float.valueOf(selectRedBag.getParValue());
                }
                mViewBinding.tvRedBag.setText(redbagTotal + "");
                float bondMoney = bondAdapter.getBuyMoney() + data.getZhf() * buyMoneyAdapter.getTime() - redbagTotal;
                mViewBinding.tvTotalMoney.setText(bondMoney + "");
            }
        }
    }


    @Override
    public int getLayoutId() {
        return R.layout.stock_buy;
    }

    @Override
    public void callBack(StockBuy data) {
        this.data = data;
        if (data.getStock() != null) {
            mViewBinding.tvStockName.setText(data.getStock().getStockName());
            mViewBinding.tvStockCode.setText("(" + data.getStock().getStockCode() + ")");
        }
        mViewBinding.tvMoney.setText(data.getBalance());
        mViewBinding.tvCanBuyNum.setText(data.getClick_times());
        mViewBinding.tvTradeMoney.setText(data.getZhf() + "");

        List<StockBuyMoney> holdList = new ArrayList<>();
        StockBuyMoney holdMoney = new StockBuyMoney();
        holdMoney.setTxt(data.getHoldTime());
        holdMoney.setType(0);
        holdList.add(holdMoney);
        holdTimeAdapter.update(holdList);

        float[] buyMoneys = data.getBuyMoney();
        if (buyMoneys != null && buyMoneys.length > 0) {
            List<StockBuyMoney> buyMoneyList = new ArrayList<>();
            for (float buyMoney : buyMoneys) {
                StockBuyMoney money = new StockBuyMoney();
                money.setTime(buyMoney);
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
                buyMoney.setType(3);
                try {
                    buyMoney.setTime(zy);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                zyList.add(buyMoney);
            }
            zyAdapter.update(zyList);
        }

        float[] bonds = data.getBond();
        if (bonds != null && bonds.length > 0) {
            List<StockBuyMoney> zsList = new ArrayList<>();
            List<StockBuyMoney> bondList = new ArrayList<>();
            for (float bond : bonds) {
                StockBuyMoney stockBuyMoney = new StockBuyMoney();
                stockBuyMoney.setTime(bond);
                stockBuyMoney.setType(2);
                bondList.add(stockBuyMoney);

                StockBuyMoney zsMoney = new StockBuyMoney();
                zsMoney.setTime(data.getZs());
                zsMoney.setType(4);
                zsList.add(zsMoney);
            }
            bondAdapter.update(bondList);
            zsAdapter.update(zsList);
        }
        refreshView(false);
    }

    private void refreshView(boolean isfreshBonde) {
        if (data == null || data.getStock() == null) {
            return;
        }
        if (isfreshBonde) {
            bondAdapter.setBaseMoney(buyMoneyAdapter.getBuyMoney());
        }
        zyAdapter.setBaseMoney(buyMoneyAdapter.getBuyMoney());
        zsAdapter.setBaseMoney(buyMoneyAdapter.getBuyMoney(), bondAdapter.getTimes(), bondAdapter.getCheckPosition());

        int buyMoney = buyMoneyAdapter.getBuyMoney();
        float mkPrice = data.getStock().getMk_price();
        int stockNum = (int) (buyMoney / (mkPrice * 100));
        stockNum *= 100;
        float shiyonglv = (mkPrice * stockNum * 100 / buyMoney);
        mViewBinding.tvNotice.setText(String.format(getString(R.string.stock_buy_can_buy_stock_num), stockNum + "", String.format("%.2f", shiyonglv) + "%"));

        mViewBinding.tvTradeMoney.setText(data.getZhf() * buyMoneyAdapter.getTime() + "");
        float bondMoney = bondAdapter.getBuyMoney() + data.getZhf() * buyMoneyAdapter.getTime() - redbagTotal;
        mViewBinding.tvTotalMoney.setText(bondMoney + "");
    }

    @Override
    public void callBack(String msg) {
        ToastUtils.showShort(msg);
    }
}