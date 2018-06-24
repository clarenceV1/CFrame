package com.cai.work.ui.stock;

import android.content.Context;
import android.view.View;

import com.cai.framework.base.GodBaseAdapter;
import com.cai.framework.bean.CBaseData;
import com.cai.framework.utils.ViewHolder;
import com.cai.work.R;
import com.cai.work.bean.Stock;

import java.util.ArrayList;
import java.util.List;

public class StockAdapter extends GodBaseAdapter {

    public StockAdapter(Context context) {
        super(context, new ArrayList());
    }

    @Override
    public void initItemView(View convertView, CBaseData itemData, int position) {
        if (itemData != null && itemData instanceof Stock) {
            Stock stock = (Stock) itemData;
            ViewHolder.getTextView(convertView, R.id.tvCode).setText(stock.getStockCode());
            ViewHolder.getTextView(convertView, R.id.tvName).setText(stock.getStockName());
        }
    }

    @Override
    protected int getItemLayout() {
        return R.layout.stock_item;
    }

    public void update(List dataList) {
        this.dataList.clear();
        this.dataList.addAll(dataList);
        notifyDataSetChanged();
    }
}
