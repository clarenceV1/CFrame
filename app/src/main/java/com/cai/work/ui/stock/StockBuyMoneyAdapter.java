package com.cai.work.ui.stock;

import android.content.Context;
import android.view.View;
import android.widget.VideoView;

import com.cai.framework.base.GodBaseAdapter;
import com.cai.framework.bean.CBaseData;
import com.cai.framework.utils.ViewHolder;
import com.cai.work.R;
import com.cai.work.bean.StockBuyMoney;

import java.util.ArrayList;
import java.util.List;

public class StockBuyMoneyAdapter extends GodBaseAdapter {

    int checkPosition = 0;

    public StockBuyMoneyAdapter(Context context) {
        super(context, new ArrayList());
    }

    @Override
    public void initItemView(View convertView, CBaseData itemData, int position) {
        if (itemData != null && itemData instanceof StockBuyMoney) {
            StockBuyMoney buyMoney = (StockBuyMoney) itemData;
            ViewHolder.getTextView(convertView, R.id.tvMoney).setText(buyMoney.getMoney());
            if (checkPosition == position) {
                ViewHolder.getImageView(convertView, R.id.xuanzdui).setVisibility(View.VISIBLE);
            } else {
                ViewHolder.getImageView(convertView, R.id.xuanzdui).setVisibility(View.GONE);
            }
        }
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
}
