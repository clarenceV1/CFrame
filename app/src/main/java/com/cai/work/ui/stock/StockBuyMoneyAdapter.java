package com.cai.work.ui.stock;

import android.content.Context;
import android.view.View;

import com.cai.framework.base.GodBaseAdapter;
import com.cai.framework.bean.CBaseData;
import com.cai.framework.utils.ViewHolder;
import com.cai.work.R;
import com.cai.work.bean.StockBuyMoney;

import java.util.ArrayList;
import java.util.List;

public class StockBuyMoneyAdapter extends GodBaseAdapter {

    int checkPosition = 0;
    int baseMoney = 10000;

    public StockBuyMoneyAdapter(Context context) {
        super(context, new ArrayList());
    }

    @Override
    public void initItemView(View convertView, CBaseData itemData, int position) {
        if (itemData != null && itemData instanceof StockBuyMoney) {
            StockBuyMoney buyMoney = (StockBuyMoney) itemData;
            if (buyMoney.getType() == 0) {
                ViewHolder.getTextView(convertView, R.id.tvMoney).setText(buyMoney.getTxt());
            } else if (buyMoney.getType() == 1) {
                int money = (int) buyMoney.getTime();
                ViewHolder.getTextView(convertView, R.id.tvMoney).setText(money + "万");
            } else {
                int money = (int) (buyMoney.getTime() * baseMoney);
                ViewHolder.getTextView(convertView, R.id.tvMoney).setText("¥" + money);
            }

            if (checkPosition == position) {
                ViewHolder.getImageView(convertView, R.id.xuanzdui).setVisibility(View.VISIBLE);
            } else {
                ViewHolder.getImageView(convertView, R.id.xuanzdui).setVisibility(View.GONE);
            }
        }
    }

    public void setBaseMoney(int baseMoney) {
        this.baseMoney = baseMoney;
        notifyDataSetChanged();
    }

    @Override
    protected int getItemLayout() {
        return R.layout.stock_buy_mony_item;
    }

    public void update(List dataList) {
        this.dataList.clear();
        this.dataList.addAll(dataList);
        notifyDataSetChanged();
    }

    public void setCheckPosition(int checkPosition) {
        this.checkPosition = checkPosition;
        notifyDataSetChanged();
    }

    public int getCheckPosition() {
        return checkPosition;
    }

    public int getBuyMoney() {
        StockBuyMoney buyMoney = (StockBuyMoney) dataList.get(checkPosition);
        if (buyMoney.getType() == 0) {
            return 0;
        }
        return (int) (buyMoney.getTime() * baseMoney);
    }
}
