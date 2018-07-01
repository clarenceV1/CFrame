package com.cai.work.ui.forward;

import android.content.Context;
import android.graphics.Color;
import android.view.View;

import com.cai.framework.base.GodBaseAdapter;
import com.cai.framework.bean.CBaseData;
import com.cai.framework.utils.ViewHolder;
import com.cai.work.R;
import com.cai.work.bean.StockBuyMoney;

import java.util.ArrayList;
import java.util.List;

public class ForwardBuyMoneyAdapter extends GodBaseAdapter {

    int checkPosition = 0;
    int baseMoney = 10000;
    int type = 1;//1涨，2 跌

    public ForwardBuyMoneyAdapter(Context context, int type) {
        super(context, new ArrayList());
        this.type = type;
    }

    @Override
    public void initItemView(View convertView, CBaseData itemData, int position) {
        if (itemData != null && itemData instanceof StockBuyMoney) {
            StockBuyMoney buyMoney = (StockBuyMoney) itemData;
            if (buyMoney.getType() == 0) {
                ViewHolder.getTextView(convertView, R.id.tvMoney).setText(buyMoney.getTxt());
            } else if (buyMoney.getType() == 1) {
                ViewHolder.getTextView(convertView, R.id.tvMoney).setText(buyMoney.getTxt() + "手");
            } else if (buyMoney.getType() == 2) {
                ViewHolder.getTextView(convertView, R.id.tvMoney).setText("¥" + buyMoney.getTxt());
            } else if (buyMoney.getType() == 3) {
                ViewHolder.getTextView(convertView, R.id.tvMoney).setText("¥" + buyMoney.getTxt());
            } else if (buyMoney.getType() == 4) {
                ViewHolder.getTextView(convertView, R.id.tvMoney).setText("¥" + buyMoney.getTxt());
            }

            if (checkPosition == position) {
                ViewHolder.getImageView(convertView, R.id.xuanzdui).setVisibility(View.VISIBLE);
            } else {
                ViewHolder.getImageView(convertView, R.id.xuanzdui).setVisibility(View.GONE);
            }
            if (type == 1) {
                ViewHolder.getTextView(convertView, R.id.tvMoney).setTextColor(context.getResources().getColor(R.color.ys_e6241a));
                ViewHolder.getView(convertView, R.id.itemRoot).setBackgroundResource(R.drawable.forward_buy_item_red_bg);
                ViewHolder.getImageView(convertView, R.id.xuanzdui).setBackgroundResource(R.drawable.jy_selectred);
            } else {
                ViewHolder.getTextView(convertView, R.id.tvMoney).setTextColor(context.getResources().getColor(R.color.ys_0_154_68));
                ViewHolder.getView(convertView, R.id.itemRoot).setBackgroundResource(R.drawable.forward_buy_item_green_bg);
                ViewHolder.getImageView(convertView, R.id.xuanzdui).setBackgroundResource(R.drawable.jy_selectgreen);
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
