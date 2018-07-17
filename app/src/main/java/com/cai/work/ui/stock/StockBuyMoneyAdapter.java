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

public class StockBuyMoneyAdapter extends GodBaseAdapter<StockBuyMoney> {

    int checkPosition = 0;
    int baseMoney = 10000;
    List<Float> boundsTimes;
    int maxSelectPosition = 0;

    public StockBuyMoneyAdapter(Context context) {
        super(context, new ArrayList());
    }

    @Override
    public void initItemView(View convertView, StockBuyMoney buyMoney, int position) {
        if (buyMoney.getType() == 0) {
            ViewHolder.getTextView(convertView, R.id.tvMoney).setText(buyMoney.getTxt());
        } else if (buyMoney.getType() == 1) {
            int money = (int) buyMoney.getTime();
            ViewHolder.getTextView(convertView, R.id.tvMoney).setText(money + "万");
        } else if (buyMoney.getType() == 4) {
            int money = (int) (buyMoney.getTime() * baseMoney);
            if (boundsTimes != null) {
                money *= boundsTimes.get(position);
                ViewHolder.getTextView(convertView, R.id.tvMoney).setText("¥" + money);
            } else {
                ViewHolder.getTextView(convertView, R.id.tvMoney).setText("¥" + money);
            }
        } else {
            int money = (int) (buyMoney.getTime() * baseMoney);
            ViewHolder.getTextView(convertView, R.id.tvMoney).setText("¥" + money);
        }
        if (buyMoney.getType() == 4 && position > maxSelectPosition) {
            ViewHolder.getView(convertView, R.id.itemRoot).setBackgroundResource(R.drawable.stock_buy_item_bg_no_choose);
            ViewHolder.getTextView(convertView, R.id.tvMoney).setTextColor(context.getResources().getColor(R.color.ys_666666));
        } else {
            ViewHolder.getView(convertView, R.id.itemRoot).setBackgroundResource(R.drawable.stock_buy_item_bg);
            ViewHolder.getTextView(convertView, R.id.tvMoney).setTextColor(context.getResources().getColor(R.color.ys_219_183_108));
        }
        if (checkPosition == position) {
            if (buyMoney.getType() == 4 && position > maxSelectPosition) {
                ViewHolder.getView(convertView, R.id.itemRoot).setBackgroundResource(R.drawable.stock_buy_item_bg_no_choose);
                ViewHolder.getImageView(convertView, R.id.xuanzdui).setVisibility(View.GONE);
            } else {
                ViewHolder.getImageView(convertView, R.id.xuanzdui).setVisibility(View.VISIBLE);
            }
        } else {
            ViewHolder.getImageView(convertView, R.id.xuanzdui).setVisibility(View.GONE);
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
     * @param boundsTime
     * @param maxSelectPosition
     */
    public void setBaseMoney(int baseMoney, List<Float> boundsTime, int maxSelectPosition) {
        this.baseMoney = baseMoney;
        this.boundsTimes = boundsTime;
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
        StockBuyMoney buyMoney = dataList.get(checkPosition);
        if (buyMoney.getType() == 0) {
            return 0;
        } else if (buyMoney.getType() == 4) {
            return (int) (buyMoney.getTime() * baseMoney * boundsTimes.get(checkPosition));
        }
        return (int) (buyMoney.getTime() * baseMoney);
    }

    public int getTime() {
        StockBuyMoney buyMoney = dataList.get(checkPosition);
        return (int) buyMoney.getTime();
    }

    public List<Float> getTimes() {
        List<Float> times = new ArrayList<>();
        for (int i = 0; i < dataList.size(); i++) {
            StockBuyMoney buyMoney = dataList.get(i);
            times.add(buyMoney.getTime());
        }
        return times;
    }
}
