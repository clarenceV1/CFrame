package com.cai.work.ui.forward;

import android.content.Context;
import android.view.View;

import com.cai.framework.base.GodBaseAdapter;
import com.cai.framework.bean.CBaseData;
import com.cai.framework.utils.ViewHolder;
import com.cai.work.R;
import com.cai.work.bean.StockBuyMoney;

import java.util.ArrayList;
import java.util.List;

public class ForwardBuyMoneyAdapter extends GodBaseAdapter<StockBuyMoney> {

    int checkPosition = 0;
    int baseMoney = 1;
    int type = 1;//1涨，2 跌
    int maxSelectPosition = 0;

    public ForwardBuyMoneyAdapter(Context context, int type) {
        super(context, new ArrayList());
        this.type = type;
    }

    @Override
    public void initItemView(View convertView, StockBuyMoney buyMoney, int position) {
        if (buyMoney.getType() == 0) {
            ViewHolder.getTextView(convertView, R.id.tvMoney).setText(buyMoney.getTxt());
        } else if (buyMoney.getType() == 1) {
            ViewHolder.getTextView(convertView, R.id.tvMoney).setText(((int) buyMoney.getTime()) + "手");
        } else if (buyMoney.getType() == 2) {
            ViewHolder.getTextView(convertView, R.id.tvMoney).setText("¥" + (buyMoney.getTime() * baseMoney));
        } else if (buyMoney.getType() == 3) {
            ViewHolder.getTextView(convertView, R.id.tvMoney).setText("¥" + (buyMoney.getTime() * baseMoney));
        } else if (buyMoney.getType() == 4) {
            ViewHolder.getTextView(convertView, R.id.tvMoney).setText("¥" + (buyMoney.getTime() * baseMoney));
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

        if (buyMoney.getType() == 4 && position > maxSelectPosition) {
            ViewHolder.getView(convertView, R.id.itemRoot).setBackgroundResource(R.drawable.stock_buy_item_bg_no_choose);
            ViewHolder.getTextView(convertView, R.id.tvMoney).setTextColor(context.getResources().getColor(R.color.ys_666666));
        }
    }

    public void setBaseMoney(int baseMoney) {
        this.baseMoney = baseMoney;
        notifyDataSetChanged();
    }

    /**
     * 止损才有用到
     *
     * @param baseMoney
     * @param maxSelectPosition
     */
    public void setBaseMoney(int baseMoney, int maxSelectPosition) {
        this.baseMoney = baseMoney;
        this.maxSelectPosition = maxSelectPosition;
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
        StockBuyMoney buyMoney = dataList.get(checkPosition);
        if (buyMoney.getType() == 4 && checkPosition > maxSelectPosition) {
            return;
        }
        this.checkPosition = checkPosition;
        notifyDataSetChanged();
    }

    public int getCheckPosition() {
        return checkPosition;
    }

    public int getBuyMoney() {
        StockBuyMoney buyMoney =dataList.get(checkPosition);
        if (buyMoney.getType() == 0) {
            return 0;
        }
        return (int) (buyMoney.getTime() * baseMoney);
    }

    public List<Float> getTimes() {
        List<Float> times = new ArrayList<>();
        for (int i = 0; i < dataList.size(); i++) {
            StockBuyMoney buyMoney = (StockBuyMoney) dataList.get(i);
            times.add(buyMoney.getTime());
        }
        return times;
    }
}
