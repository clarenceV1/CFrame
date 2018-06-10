package com.cai.work.ui.fund;

import android.content.Context;
import android.view.View;

import com.cai.framework.base.GodBaseAdapter;
import com.cai.framework.bean.CBaseData;
import com.cai.framework.utils.ViewHolder;
import com.cai.work.R;
import com.cai.work.bean.FundDetailItem;

import java.util.ArrayList;
import java.util.List;

class FundDetailAdapter extends GodBaseAdapter {

    public FundDetailAdapter(Context context) {
        super(context, new ArrayList());
    }

    public void update(List data) {
        if (data != null) {
            dataList.addAll(data);
            notifyDataSetChanged();
        }
    }

    @Override
    public void initItemView(View convertView, CBaseData itemData, int position) {
        if (itemData != null && itemData instanceof FundDetailItem) {
            FundDetailItem fundDetail = (FundDetailItem) itemData;
            ViewHolder.getTextView(convertView, R.id.tvRelatedType).setText(fundDetail.getRelatedType());
            if (fundDetail.getInoutType() == 1) {
                ViewHolder.getTextView(convertView, R.id.tvAmount).setText("+" + fundDetail.getAmount());
            } else {
                ViewHolder.getTextView(convertView, R.id.tvAmount).setText("-" + fundDetail.getAmount());
            }
            ViewHolder.getTextView(convertView, R.id.tvRemark).setText(fundDetail.getRemark());
            ViewHolder.getTextView(convertView, R.id.tvCreateDate).setText(fundDetail.getCreateDate());
        }
    }

    @Override
    protected int getItemLayout() {
        return R.layout.fund_detail_item;
    }
}
