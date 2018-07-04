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
import com.cai.work.bean.StockAccount;
import com.cai.work.bean.StockHold;
import com.cai.work.dialog.SellDialog;
import com.example.clarence.utillibrary.ToastUtils;

import java.util.ArrayList;
import java.util.List;

public class MainHoldAdapter extends GodBaseAdapter {

    public MainHoldAdapter(Context context) {
        super(context, new ArrayList());
    }

    public void addAll(List data) {
        if (data != null && data.size() > 0) {
            dataList.addAll(data);
            notifyDataSetChanged();
        }
    }

    public void update(List data) {
        dataList.clear();
        dataList.addAll(data);
        notifyDataSetChanged();
    }

    @Override
    public void initItemView(View convertView, CBaseData itemData, int position) {
        if (itemData != null && itemData instanceof StockHold) { //股票持仓
            stockHold(convertView, itemData);
        } else if (itemData != null && itemData instanceof StockAccount) {
            stockAccount(convertView, itemData);
        } else if (itemData != null && itemData instanceof ForwardAccount) {
            forwardAccount(convertView, itemData);
        }
    }

    /**
     * 实盘/模拟--股票持仓
     */
    private void stockHold(View convertView, CBaseData itemData) {
        final StockHold item = (StockHold) itemData;
        ViewHolder.getTextView(convertView, R.id.tvBuyDealDate).setText("点买时间：" + item.getBuyDealDate());
        String tag = "SZ";
        if ("2".equals(item.getMarketType())) {
            tag = "CH";
        }
        ViewHolder.getTextView(convertView, R.id.tvStockName).setText(item.getStockName());
        ViewHolder.getTextView(convertView, R.id.tvstockCode).setText(tag + item.getStockCode());
        ViewHolder.getTextView(convertView, R.id.tvYkMoney).setText(item.getYkMoney());

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
                    SellDialog dialog = new SellDialog(context, item);
                    dialog.show();
                }
            });
        } else {
            ViewHolder.getButton(convertView, R.id.btnCommit).setBackgroundResource(R.drawable.btn_gray);
            ViewHolder.getButton(convertView, R.id.btnCommit).setClickable(false);
        }
    }

    /**
     * 实盘/模拟--股票结算
     */
    private void stockAccount(View convertView, CBaseData itemData) {
        final StockAccount item = (StockAccount) itemData;
        ViewHolder.getTextView(convertView, R.id.tvBuyDealDate).setText("点买时间：" + item.getBuyDealDate());
        String tag = "SZ";
        if ("2".equals(item.getMarketType())) {
            tag = "CH";
        }
        ViewHolder.getTextView(convertView, R.id.tvStockName).setText(item.getStockName());
        ViewHolder.getTextView(convertView, R.id.tvstockCode).setText(tag + item.getStockCode());
        ViewHolder.getTextView(convertView, R.id.tvYkMoney).setText(item.getYkMoney());

        ViewHolder.getTextView(convertView, R.id.tvTitle1).setText(context.getResources().getString(R.string.main_hold_buy_price));
        ViewHolder.getTextView(convertView, R.id.tvValue1).setText(item.getBuyPrice() + "元");

        ViewHolder.getTextView(convertView, R.id.tvTitle2).setText(context.getResources().getString(R.string.main_hold_sell_price));
        ViewHolder.getTextView(convertView, R.id.tvValue2).setText(item.getSellPrice() + "元");

        ViewHolder.getTextView(convertView, R.id.tvTitle3).setText(context.getResources().getString(R.string.main_hold_buy_num));
        ViewHolder.getTextView(convertView, R.id.tvValue3).setText(item.getDealAmount() + "股");

        ViewHolder.getTextView(convertView, R.id.tvTitle4).setVisibility(View.GONE);
        ViewHolder.getTextView(convertView, R.id.tvValue4).setVisibility(View.GONE);
        ViewHolder.getButton(convertView, R.id.btnCommit).setBackgroundResource(R.drawable.btn_red);
        ViewHolder.getButton(convertView, R.id.btnCommit).setText(context.getResources().getString(R.string.btn_look_detail));
        ViewHolder.getButton(convertView, R.id.btnCommit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.showShort("股票结算");
                ARouter.getInstance().build("/AppModule/AccountsDetailActivity")
                        .withCharSequence("jsonStr", JSON.toJSONString(item))
                        .withInt("type", 1)
                        .navigation();
            }
        });
    }

    /**
     * 实盘/模拟--期货结算
     */
    private void forwardAccount(View convertView, CBaseData itemData) {
        final ForwardAccount item = (ForwardAccount) itemData;
        ViewHolder.getTextView(convertView, R.id.tvBuyDealDate).setText("平仓时间：" + item.getCloseDealDate());
        ViewHolder.getTextView(convertView, R.id.tvStockName).setText(item.getContractName());
        ViewHolder.getTextView(convertView, R.id.tvstockCode).setText(item.getContractCode());
        ViewHolder.getTextView(convertView, R.id.tvYkMoney).setText(item.getYkMoney() + "(" + item.getApproveStateText() + ")");

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
        } else {
            ViewHolder.getTextView(convertView, R.id.tvValue4).setText("买涨");
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
    }

    @Override
    protected int getItemLayout() {
        return R.layout.main_hold_item;
    }
}
