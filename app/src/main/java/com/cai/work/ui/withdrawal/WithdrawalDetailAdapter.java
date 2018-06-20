package com.cai.work.ui.withdrawal;

import android.content.Context;
import android.view.View;

import com.cai.framework.base.GodBaseAdapter;
import com.cai.framework.bean.CBaseData;
import com.cai.framework.utils.ViewHolder;
import com.cai.work.R;
import com.cai.work.bean.WithdrawalDetailItem;

import java.util.ArrayList;
import java.util.List;

class WithdrawalDetailAdapter extends GodBaseAdapter {

    public WithdrawalDetailAdapter(Context context) {
        super(context, new ArrayList());
    }

    public void update(List data) {
        if (data != null) {
            dataList.addAll(data);
            notifyDataSetChanged();
        }
    }

    public void updateAndClean(List data) {
        if (data != null) {
            dataList.clear();
            dataList.addAll(data);
            notifyDataSetChanged();
        }
    }
    @Override
    public void initItemView(final View convertView, CBaseData itemData, int position) {
        if (itemData != null && itemData instanceof WithdrawalDetailItem) {
            WithdrawalDetailItem detailItem = (WithdrawalDetailItem) itemData;
            ViewHolder.getTextView(convertView, R.id.tvTime).setText(detailItem.getOrderDate());
            ViewHolder.getTextView(convertView, R.id.tvAmount).setText(detailItem.getAmount());
            String stateStr ;
            if (detailItem.getOrderState() == 1) {
                stateStr = "待审核";
            } else if (detailItem.getOrderState() == 2) {
                stateStr = "提现成功";
            } else {
                stateStr = "提现失败";
            }
            ViewHolder.getTextView(convertView, R.id.tvState).setText(stateStr);
        }
    }

    @Override
    protected int getItemLayout() {
        return R.layout.withdrawal_detail_item;
    }
}
