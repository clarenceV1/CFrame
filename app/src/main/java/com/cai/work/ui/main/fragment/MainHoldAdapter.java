package com.cai.work.ui.main.fragment;

import android.content.Context;
import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.alibaba.fastjson.JSON;
import com.cai.framework.base.GodBaseAdapter;
import com.cai.framework.bean.CBaseData;
import com.cai.framework.utils.ViewHolder;
import com.cai.work.R;
import com.cai.work.bean.ForwardAccount;
import com.cai.work.bean.ForwardHold;
import com.cai.work.bean.StockAccount;
import com.cai.work.bean.StockHold;
import com.cai.work.dialog.SellDialog;

import java.util.ArrayList;
import java.util.List;

public class MainHoldAdapter extends GodBaseAdapter {

    boolean isRealTrade;
    MainHoldPresenter presenter;

    public MainHoldAdapter(Context context, MainHoldPresenter presenter) {
        super(context, new ArrayList());
        this.presenter = presenter;
    }

    public void addAll(List data) {
        if (data != null && data.size() > 0) {
            dataList.addAll(data);
            notifyDataSetChanged();
        }
    }

    public void update(List data,boolean isRealTrade) {
        this.isRealTrade = isRealTrade;
        dataList.clear();
        dataList.addAll(data);
        notifyDataSetChanged();
    }

    @Override
    public void initItemView(View convertView, CBaseData itemData, int position) {
        if (itemData != null && itemData instanceof StockHold) { //股票持仓
            StockHold stockHold = (StockHold) itemData;
            stockHold(convertView, stockHold);
        } else if (itemData != null && itemData instanceof StockAccount) { //股票结算
            StockAccount stockAccount = (StockAccount) itemData;
            stockAccount(convertView, stockAccount);
        } else if (itemData != null && itemData instanceof ForwardAccount) { //期货结算
            ForwardAccount forwardAccount = (ForwardAccount) itemData;
            forwardAccount(convertView, forwardAccount);
        } else if (itemData != null && itemData instanceof ForwardHold) { //期货持仓
            ForwardHold forwardHold = (ForwardHold) itemData;
            forwardHold(convertView, forwardHold);
        }
    }

    /**
     * 实盘/模拟--股票持仓
     */
    private void stockHold(View convertView, final StockHold item) {
        ViewHolder.getTextView(convertView, R.id.tvBuyDealDate).setText("点买时间：" + item.getBuyDealDate());
        String tag = "SZ";
        if ("2".equals(item.getMarketType())) {
            tag = "CH";
        }
        ViewHolder.getTextView(convertView, R.id.tvStockName).setText(item.getStockName());
        ViewHolder.getTextView(convertView, R.id.tvstockCode).setText(tag + item.getStockCode());
        ViewHolder.getTextView(convertView, R.id.tvYkMoney).setText(item.getYkMoney());
        if (item.getYkMoney() != null && item.getYkMoney().contains("-")) {
            ViewHolder.getTextView(convertView, R.id.tvValue2).setTextColor(context.getResources().getColor(R.color.ys_009a44));
            ViewHolder.getTextView(convertView, R.id.tvYkMoney).setTextColor(context.getResources().getColor(R.color.ys_009a44));
        } else {
            ViewHolder.getTextView(convertView, R.id.tvYkMoney).setTextColor(context.getResources().getColor(R.color.ys_e6241a));
            ViewHolder.getTextView(convertView, R.id.tvValue2).setTextColor(context.getResources().getColor(R.color.ys_e6241a));
        }

        ViewHolder.getTextView(convertView, R.id.tvTitle1).setText(context.getResources().getString(R.string.main_hold_buy_price));
        ViewHolder.getTextView(convertView, R.id.tvValue1).setText(item.getBuyPrice() + "元");
        ViewHolder.getTextView(convertView, R.id.tvTitle2).setText(context.getResources().getString(R.string.main_hold_current_price));
        ViewHolder.getTextView(convertView, R.id.tvValue2).setText(item.getMarketPrice() + "元");


        ViewHolder.getTextView(convertView, R.id.tvTitle3).setText(context.getResources().getString(R.string.main_hold_buy_num));
        ViewHolder.getTextView(convertView, R.id.tvValue3).setText(item.getDealAmount() + "股");

        ViewHolder.getTextView(convertView, R.id.tvTitle4).setVisibility(View.GONE);
        ViewHolder.getTextView(convertView, R.id.tvValue4).setVisibility(View.GONE);

        ViewHolder.getButton(convertView, R.id.btnCommit).setText(item.getBtnText());
        if ("1".equals(item.getIsTrade())) {
            ViewHolder.getButton(convertView, R.id.btnCommit).setBackgroundResource(R.drawable.btn_red);
            ViewHolder.getButton(convertView, R.id.btnCommit).setClickable(true);
            ViewHolder.getButton(convertView, R.id.btnCommit).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SellDialog dialog = new SellDialog(context, item,presenter);
                    dialog.show();
                }
            });
        } else {
            ViewHolder.getButton(convertView, R.id.btnCommit).setBackgroundResource(R.drawable.btn_gray);
            ViewHolder.getButton(convertView, R.id.btnCommit).setClickable(false);
        }
        ViewHolder.getView(convertView, R.id.llChicang).setVisibility(View.GONE);
        ViewHolder.getButton(convertView, R.id.btnCommit).setVisibility(View.VISIBLE);
    }

    /**
     * 实盘/模拟--股票结算
     */
    private void stockAccount(View convertView, final StockAccount item) {
        ViewHolder.getTextView(convertView, R.id.tvBuyDealDate).setText("点买时间：" + item.getBuyDealDate());
        String tag = "SZ";
        if ("2".equals(item.getMarketType())) {
            tag = "SH";
        }
        ViewHolder.getTextView(convertView, R.id.tvStockName).setText(item.getStockName());
        ViewHolder.getTextView(convertView, R.id.tvstockCode).setText(tag + item.getStockCode());
        ViewHolder.getTextView(convertView, R.id.tvYkMoney).setText(item.getYkMoney());
        if (item.getYkMoney() != null && item.getYkMoney().contains("-")) {
            ViewHolder.getTextView(convertView, R.id.tvYkMoney).setTextColor(context.getResources().getColor(R.color.ys_009a44));
        } else {
            ViewHolder.getTextView(convertView, R.id.tvYkMoney).setTextColor(context.getResources().getColor(R.color.ys_e6241a));
        }

        ViewHolder.getTextView(convertView, R.id.tvTitle1).setText(context.getResources().getString(R.string.main_hold_buy_price));
        ViewHolder.getTextView(convertView, R.id.tvValue1).setText(item.getBuyPrice() + "元");
        ViewHolder.getTextView(convertView, R.id.tvTitle2).setText(context.getResources().getString(R.string.main_hold_sell_price));
        ViewHolder.getTextView(convertView, R.id.tvValue2).setText(item.getSellPrice() + "元");
        ViewHolder.getTextView(convertView, R.id.tvValue2).setTextColor(context.getResources().getColor(R.color.ys_255_255_255));

        ViewHolder.getTextView(convertView, R.id.tvTitle3).setText(context.getResources().getString(R.string.main_hold_buy_num));
        ViewHolder.getTextView(convertView, R.id.tvValue3).setText(item.getDealAmount() + "股");

        ViewHolder.getTextView(convertView, R.id.tvTitle4).setVisibility(View.GONE);
        ViewHolder.getTextView(convertView, R.id.tvValue4).setVisibility(View.GONE);
        ViewHolder.getButton(convertView, R.id.btnCommit).setBackgroundResource(R.drawable.btn_red);
        ViewHolder.getButton(convertView, R.id.btnCommit).setText(context.getResources().getString(R.string.btn_look_detail));
        ViewHolder.getButton(convertView, R.id.btnCommit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance().build("/AppModule/AccountsDetailActivity")
                        .withCharSequence("jsonStr", JSON.toJSONString(item))
                        .withInt("type", 1)
                        .navigation();
            }
        });
        ViewHolder.getView(convertView, R.id.llChicang).setVisibility(View.GONE);
        ViewHolder.getButton(convertView, R.id.btnCommit).setVisibility(View.VISIBLE);
    }

    /**
     * 实盘/模拟--期货结算
     */
    private void forwardAccount(View convertView, final ForwardAccount item) {
        ViewHolder.getTextView(convertView, R.id.tvBuyDealDate).setText("平仓时间：" + item.getCloseDealDate());
        ViewHolder.getTextView(convertView, R.id.tvStockName).setText(item.getContractName());
        ViewHolder.getTextView(convertView, R.id.tvstockCode).setText(item.getContractCode());
        ViewHolder.getTextView(convertView, R.id.tvYkMoney).setText(item.getYkMoney()+" (" + item.getApproveStateText() + ")");
        if (item.getYkMoney() != null && item.getYkMoney().contains("-")) {
            ViewHolder.getTextView(convertView, R.id.tvYkMoney).setTextColor(context.getResources().getColor(R.color.ys_009a44));
        } else {
            ViewHolder.getTextView(convertView, R.id.tvYkMoney).setTextColor(context.getResources().getColor(R.color.ys_e6241a));
        }
        ViewHolder.getTextView(convertView, R.id.tvTitle1).setText(context.getResources().getString(R.string.main_hold_open_price));
        ViewHolder.getTextView(convertView, R.id.tvValue1).setText(item.getOpenPrice());

        ViewHolder.getTextView(convertView, R.id.tvTitle2).setText(context.getResources().getString(R.string.main_hold_close_price));
        ViewHolder.getTextView(convertView, R.id.tvValue2).setText(item.getClosePrice());

        ViewHolder.getTextView(convertView, R.id.tvTitle3).setText(context.getResources().getString(R.string.main_hold_open_num));
        ViewHolder.getTextView(convertView, R.id.tvValue3).setText(item.getBuyAmount() + "手");

        ViewHolder.getTextView(convertView, R.id.tvTitle4).setVisibility(View.VISIBLE);
        ViewHolder.getTextView(convertView, R.id.tvValue4).setVisibility(View.VISIBLE);
        ViewHolder.getTextView(convertView, R.id.tvTitle4).setText(context.getResources().getString(R.string.main_hold_open_direction));
        if ("2".equals(item.getBuyWay())) {
            ViewHolder.getTextView(convertView, R.id.tvValue4).setText("买跌");
            ViewHolder.getTextView(convertView, R.id.tvValue4).setTextColor(context.getResources().getColor(R.color.ys_009a44));
        } else {
            ViewHolder.getTextView(convertView, R.id.tvValue4).setText("买涨");
            ViewHolder.getTextView(convertView, R.id.tvValue4).setTextColor(context.getResources().getColor(R.color.ys_e6241a));
        }
        ViewHolder.getButton(convertView, R.id.btnCommit).setBackgroundResource(R.drawable.btn_red);
        ViewHolder.getButton(convertView, R.id.btnCommit).setText(context.getResources().getString(R.string.btn_look_detail));
        ViewHolder.getButton(convertView, R.id.btnCommit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance().build("/AppModule/AccountsDetailActivity")
                        .withCharSequence("jsonStr", JSON.toJSONString(item))
                        .withInt("type", 2)
                        .navigation();
            }
        });
        ViewHolder.getView(convertView, R.id.llChicang).setVisibility(View.GONE);
        ViewHolder.getButton(convertView, R.id.btnCommit).setVisibility(View.VISIBLE);
    }

    /**
     * 期货持仓
     *
     * @param convertView
     * @param item
     */
    private void forwardHold(View convertView, final ForwardHold item) {
        ViewHolder.getTextView(convertView, R.id.tvBuyDealDate).setText("开仓时间：" + item.getOpenData() + " " + item.getOpenTime());
        ViewHolder.getTextView(convertView, R.id.tvStockName).setText(item.getContractName());
        ViewHolder.getTextView(convertView, R.id.tvstockCode).setText(item.getContractCode());
        ViewHolder.getTextView(convertView, R.id.tvYkMoney).setText(item.getYkMoney());
        if (item.getYkMoney() != null && item.getYkMoney().contains("-")) {
            ViewHolder.getTextView(convertView, R.id.tvYkMoney).setTextColor(context.getResources().getColor(R.color.ys_009a44));
        } else {
            ViewHolder.getTextView(convertView, R.id.tvYkMoney).setTextColor(context.getResources().getColor(R.color.ys_e6241a));
        }
        ViewHolder.getTextView(convertView, R.id.tvTitle1).setText("开仓价格");
        ViewHolder.getTextView(convertView, R.id.tvValue1).setText(item.getOpenPrice() + "元");
        ViewHolder.getTextView(convertView, R.id.tvValue2).setText(item.getMk_price() + "元");
        ViewHolder.getTextView(convertView, R.id.tvValue2).setTextColor(context.getResources().getColor(R.color.ys_255_255_255));

        ViewHolder.getTextView(convertView, R.id.tvTitle3).setText(context.getResources().getString(R.string.main_hold_open_num));
        ViewHolder.getTextView(convertView, R.id.tvValue3).setText(item.getBuyAmount() + "手");

        ViewHolder.getTextView(convertView, R.id.tvTitle4).setVisibility(View.VISIBLE);
        ViewHolder.getTextView(convertView, R.id.tvTitle4).setText(context.getResources().getString(R.string.main_hold_open_direction));
        ViewHolder.getTextView(convertView, R.id.tvValue4).setVisibility(View.VISIBLE);
        if ("2".equals(item.getBuyWay())) {
            ViewHolder.getTextView(convertView, R.id.tvValue4).setText("买跌");
            ViewHolder.getTextView(convertView, R.id.tvValue4).setTextColor(context.getResources().getColor(R.color.ys_009a44));
        } else {
            ViewHolder.getTextView(convertView, R.id.tvValue4).setText("买涨");
            ViewHolder.getTextView(convertView, R.id.tvValue4).setTextColor(context.getResources().getColor(R.color.ys_e6241a));
        }
        ViewHolder.getView(convertView, R.id.llChicang).setVisibility(View.VISIBLE);
        ViewHolder.getButton(convertView, R.id.btnCommit).setVisibility(View.GONE);
        ViewHolder.getButton(convertView, R.id.btnPincang).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.postPingCang(isRealTrade,item.getId()+"",item.getContractCode());
            }
        });
        ViewHolder.getButton(convertView, R.id.btnFanshou).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.postFanshou(isRealTrade,item.getId()+"",item.getContractCode());
            }
        });
    }

    @Override
    protected int getItemLayout() {
        return R.layout.main_hold_item;
    }
}
