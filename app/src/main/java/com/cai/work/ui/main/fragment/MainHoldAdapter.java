package com.cai.work.ui.main.fragment;

import android.content.Context;
import android.view.View;

import com.cai.framework.base.GodBaseAdapter;
import com.cai.framework.bean.CBaseData;
import com.cai.framework.utils.ViewHolder;
import com.cai.work.R;
import com.cai.work.bean.StockHold;

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
            StockHold item = (StockHold) itemData;
            ViewHolder.getTextView(convertView, R.id.tvBuyDealDate).setText("点买时间：" + item.getBuyDealDate());
            String tag = "SZ";
            if ("2".equals(item.getMarketType())) {
                tag = "CH";
            }
            ViewHolder.getTextView(convertView, R.id.tvStockName).setText(item.getStockName());
            ViewHolder.getTextView(convertView, R.id.tvstockCode).setText(tag + item.getStockCode());
            ViewHolder.getTextView(convertView, R.id.tvYkMoney).setText(item.getYkMoney());

            ViewHolder.getTextView(convertView, R.id.tvTitle1).setText(context.getResources().getString(R.string.main_hold_buy_price));
            ViewHolder.getTextView(convertView, R.id.tvValue1).setText(item.getBuyPrice());

            ViewHolder.getTextView(convertView, R.id.tvTitle2).setText(context.getResources().getString(R.string.main_hold_current_price));
            ViewHolder.getTextView(convertView, R.id.tvValue2).setText(item.getMarketPrice());

            ViewHolder.getTextView(convertView, R.id.tvTitle3).setText(context.getResources().getString(R.string.main_hold_buy_num));
            ViewHolder.getTextView(convertView, R.id.tvValue3).setText(item.getDealAmount() + "股");

            ViewHolder.getTextView(convertView, R.id.tvTitle4).setVisibility(View.GONE);
            ViewHolder.getTextView(convertView, R.id.tvValue4).setVisibility(View.GONE);
        }
    }

    @Override
    protected int getItemLayout() {
        return R.layout.main_hold_item;
    }
}
