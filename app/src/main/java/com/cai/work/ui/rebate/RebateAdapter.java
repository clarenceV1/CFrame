package com.cai.work.ui.rebate;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cai.framework.base.GodBaseAdapter;
import com.cai.framework.bean.CBaseData;
import com.cai.framework.utils.ViewHolder;
import com.cai.work.R;
import com.cai.work.bean.RebateItem;

import java.util.ArrayList;
import java.util.List;

public class RebateAdapter extends GodBaseAdapter {

    public RebateAdapter(Context context) {
        super(context, new ArrayList());
    }

    public <T> void update(List<T> dataList) {
        this.dataList = dataList;
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(getItemLayout(), parent, false);
            LinearLayout row = (LinearLayout) convertView.findViewById(R.id.rlRoot);  // 行布局
            View view = LayoutInflater.from(context).inflate(R.layout.rebate_item_item, null);
            row.addView(view, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT, 1.0f));
        }
        RebateItem rebateItem = (RebateItem) dataList.get(position);
        ViewHolder.getTextView(convertView, R.id.tvTime).setText(rebateItem.getOrder_date());
        ViewHolder.getTextView(convertView, R.id.tvPerson).setText(rebateItem.getRealName());
        ViewHolder.getTextView(convertView, R.id.tvProduct).setText(rebateItem.getProduct());
        ViewHolder.getTextView(convertView, R.id.tvPrice).setText(rebateItem.getInterestMoney());
        ViewHolder.getTextView(convertView, R.id.tvRebate).setText(rebateItem.getFeeCharge());
        if (rebateItem.getIsWithdraw() == 2) {
            CheckBox checkBox = ViewHolder.getView(convertView, R.id.checkbox);
            checkBox.setChecked(rebateItem.isChoosed());
            checkBox.setVisibility(View.VISIBLE);
        } else {
            ViewHolder.getView(convertView, R.id.checkbox).setVisibility(View.INVISIBLE);
        }
        return convertView;
    }

    @Override
    public void initItemView(View convertView, CBaseData itemData, int position) {

    }

    @Override
    protected int getItemLayout() {
        return R.layout.rebate_item;
    }
}
