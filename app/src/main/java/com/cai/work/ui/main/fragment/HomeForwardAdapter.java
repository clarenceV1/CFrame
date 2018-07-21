package com.cai.work.ui.main.fragment;

import android.content.Context;
import android.view.View;

import com.cai.framework.base.GodBaseAdapter;
import com.cai.framework.bean.CBaseData;
import com.cai.framework.utils.ViewHolder;
import com.cai.framework.widget.CircleView;
import com.cai.work.R;
import com.cai.work.bean.home.HomeNphyData;
import com.cai.work.bean.home.HomeWphyData;

import java.util.ArrayList;
import java.util.List;

public class HomeForwardAdapter extends GodBaseAdapter {

    public HomeForwardAdapter(Context context) {
        super(context, new ArrayList());
    }

    public void update(List dataList) {
        this.dataList = dataList;
        notifyDataSetChanged();
    }

    @Override
    public void initItemView(View convertView, CBaseData itemData, int position) {
        int isTrade = 1;
        String shortCode = null;
        String color = null;
        String bond = null;
        String tradeTime = null;
        String contractName = null;
        String remark = null;
        if (itemData instanceof HomeNphyData) {
            HomeNphyData nphyData = (HomeNphyData) itemData;
            isTrade = nphyData.getIsTrade();
            color = nphyData.getColor();
            shortCode = nphyData.getShortCode();
            bond = nphyData.getBond();
            tradeTime = nphyData.getTradeTime();
            contractName = nphyData.getContractName();
            remark = nphyData.getRemark();
        } else if (itemData instanceof HomeWphyData) {
            HomeWphyData wphyData = (HomeWphyData) itemData;
            isTrade = wphyData.getIsTrade();
            color = wphyData.getColor();
            shortCode = wphyData.getShortCode();
            bond = wphyData.getBond();
            tradeTime = wphyData.getTradeTime();
            contractName = wphyData.getContractName();
            remark = wphyData.getRemark();
        }

        if (isTrade == 1) {
            ViewHolder.getTextView(convertView, R.id.tvBound).setTextColor(context.getResources().getColor(R.color.ys_241_83_83));
            ViewHolder.getTextView(convertView, R.id.tvStockNmae).setTextColor(context.getResources().getColor(R.color.ys_255_255_255));
            ViewHolder.getTextView(convertView, R.id.tvTradeState).setText(context.getResources().getString(R.string.home_trade_ing));
            ViewHolder.getTextView(convertView, R.id.tvTradeState).setBackgroundResource(R.drawable.stock_code_bg);
        } else {
            ViewHolder.getTextView(convertView, R.id.tvBound).setTextColor(context.getResources().getColor(R.color.ys_102_102_102));
            ViewHolder.getTextView(convertView, R.id.tvStockNmae).setTextColor(context.getResources().getColor(R.color.ys_102_102_102));
            ViewHolder.getTextView(convertView, R.id.tvTradeState).setText(context.getResources().getString(R.string.home_trade_end));
            ViewHolder.getTextView(convertView, R.id.tvTradeState).setBackgroundResource(R.drawable.stock_code_gray_bg);
        }
        if (color != null) {
            ((CircleView) ViewHolder.getView(convertView, R.id.circleView)).setColor("#" + color);
        }
        if (shortCode != null) {
            ViewHolder.getTextView(convertView, R.id.tvShortCode).setText(shortCode);
        }
        if (bond != null) {
            ViewHolder.getTextView(convertView, R.id.tvBound).setText("¥" + bond);
        }
        if (tradeTime != null) {
            ViewHolder.getTextView(convertView, R.id.tvTradeTime).setText(tradeTime);
        }
        if (contractName != null) {
            ViewHolder.getTextView(convertView, R.id.tvStockNmae).setText(contractName);
        }
        if (remark != null) {
            ViewHolder.getTextView(convertView, R.id.tvRemark).setText(remark);
        }
    }

    @Override
    protected int getItemLayout() {
        return R.layout.home_forward_item;
    }
}
