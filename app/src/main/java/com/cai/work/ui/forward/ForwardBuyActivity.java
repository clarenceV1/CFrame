package com.cai.work.ui.forward;

import android.os.Bundle;
import android.text.TextUtils;
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
import com.cai.work.bean.ForwardBuy;
import com.cai.work.bean.StockBuyMoney;
import com.cai.work.databinding.ForwardBuyBinding;
import com.example.clarence.utillibrary.ToastUtils;

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
                refreshView(true);
            }
        });

        boundAdapter = new ForwardBuyMoneyAdapter(this, type);
        mViewBinding.gridViewBond.setAdapter(boundAdapter);
        mViewBinding.gridViewBond.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                boundAdapter.setCheckPosition(position);
                refreshView(false);
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
        mViewBinding.tvSelectRedBag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (data != null && data.getRedBags()!=null) {
                    ARouter.getInstance().build("/AppModule/RedPacketSelectActivity").withCharSequence("datas", JSON.toJSONString(data.getRedBags())).navigation();
                } else {
                    ToastUtils.showShort("您没有可用的红包！");
                }
            }
        });
        presenter.requestForwardBuy(forwardCode);
    }

    private void refreshView(boolean bondRefresh) {
        if (bondRefresh) {
            boundAdapter.setBaseMoney(amountAdapter.getBuyMoney());
        }
        zyAdapter.setBaseMoney(amountAdapter.getBuyMoney());
        zsAdapter.setBaseMoney(amountAdapter.getBuyMoney(), boundAdapter.getCheckPosition());
        mViewBinding.tvTradeMoney.setText(data.getCost() * (amountAdapter.getCheckPosition() + 1) + "");
        int totalMoney = (int) (boundAdapter.getBuyMoney() + data.getCost() * (amountAdapter.getCheckPosition() + 1));
        mViewBinding.tvTotalMoney.setText(totalMoney + "");
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

        if (type == 1) {
            mViewBinding.tvMarkPrice.setTextColor(getResources().getColor(R.color.ys_e6241a));
            mViewBinding.rlMarkPrice.setBackgroundResource(R.drawable.forward_buy_item_red_bg);
            mViewBinding.imgeMarkPrice.setBackgroundResource(R.drawable.jy_selectred);
        } else {
            mViewBinding.tvMarkPrice.setTextColor(getResources().getColor(R.color.ys_0_154_68));
            mViewBinding.rlMarkPrice.setBackgroundResource(R.drawable.forward_buy_item_green_bg);
            mViewBinding.imgeMarkPrice.setBackgroundResource(R.drawable.jy_selectgreen);
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
        mViewBinding.tvTradeMoney.setText(data.getCost() + "");
        mViewBinding.tvHoldTime.setText(data.getNightTime());

        int[] amounts = data.getAmount();
        if (amounts != null && amounts.length > 0) {
            List<StockBuyMoney> buyMoneyList = new ArrayList<>();
            for (int amount : amounts) {
                StockBuyMoney money = new StockBuyMoney();
                money.setTime(amount);
                money.setType(1);
                buyMoneyList.add(money);
            }
            amountAdapter.update(buyMoneyList);
        }

        float[] bonds = data.getBond();
        if (bonds != null && bonds.length > 0) {
            List<StockBuyMoney> buyMoneyList = new ArrayList<>();
            for (float bond : bonds) {
                StockBuyMoney money = new StockBuyMoney();
                money.setTime(bond);
                money.setType(2);
                buyMoneyList.add(money);
            }
            boundAdapter.update(buyMoneyList);
        }

        float[] zys = data.getZy();
        if (zys != null && zys.length > 0) {
            List<StockBuyMoney> buyMoneyList = new ArrayList<>();
            for (float zy : zys) {
                StockBuyMoney money = new StockBuyMoney();
                money.setTime(zy);
                money.setType(3);
                buyMoneyList.add(money);
            }
            zyAdapter.update(buyMoneyList);
        }

        float[] zss = data.getZs();
        if (zss != null && zss.length > 0) {
            List<StockBuyMoney> buyMoneyList = new ArrayList<>();
            for (float zs : zss) {
                StockBuyMoney money = new StockBuyMoney();
                money.setTime(zs);
                money.setType(4);
                buyMoneyList.add(money);
            }
            zsAdapter.update(buyMoneyList);
        }
        refreshView(true);
    }

    @Override
    public void toast(String msg) {

    }
}
