package com.cai.work.ui.rebate;

import android.content.Context;
import android.view.View;
import android.widget.CheckBox;

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
    public void initItemView(View convertView, CBaseData itemData, int position) {
        if (itemData instanceof RebateItem) {
            RebateItem rebateItem = (RebateItem) itemData;
            ViewHolder.getTextView(convertView, R.id.tvTime).setText(rebateItem.getOrder_date());
            ViewHolder.getTextView(convertView, R.id.tvPerson).setText(rebateItem.getRealName());
            ViewHolder.getTextView(convertView, R.id.tvProduct).setText(rebateItem.getProduct());
            ViewHolder.getTextView(convertView, R.id.tvPrice).setText(rebateItem.getFeeCharge());
            if (rebateItem.getIsWithdraw() == 2) {
                CheckBox checkBox = ViewHolder.getView(convertView, R.id.checkbox);
                checkBox.setChecked(rebateItem.isChoosed());
                checkBox.setVisibility(View.VISIBLE);
            } else {
                ViewHolder.getView(convertView, R.id.checkbox).setVisibility(View.INVISIBLE);
            }
        }
    }

    @Override
    protected int getItemLayout() {
        return R.layout.rebate_item;
    }
}
