package com.cai.work.ui.redPacket;

import android.content.Context;
import android.view.View;

import com.cai.framework.base.GodBaseAdapter;
import com.cai.framework.utils.ViewHolder;
import com.cai.work.R;
import com.cai.work.bean.StockBuyRedBag;

import java.util.ArrayList;
import java.util.List;

class RedPacketSelectAdapter extends GodBaseAdapter<StockBuyRedBag> {

    public RedPacketSelectAdapter(Context context, List data) {
        super(context, data);
    }

    @Override
    public void initItemView(final View convertView, StockBuyRedBag redPacketItem, int position) {
        ViewHolder.getTextView(convertView, R.id.tvParValue).setText(redPacketItem.getParValue());
        ViewHolder.getTextView(convertView, R.id.tvDueDate).setText(redPacketItem.getFailureTime());
        ViewHolder.getTextView(convertView, R.id.tvDueDate).setText(redPacketItem.getFailureTime());
        if (redPacketItem.isSelect()) {
            ViewHolder.getView(convertView, R.id.llContain).setBackgroundResource(R.drawable.syhb_selectbg);
        } else {
            ViewHolder.getView(convertView, R.id.llContain).setBackgroundResource(R.drawable.syhb_unselectbg);
        }
    }

    @Override
    protected int getItemLayout() {
        return R.layout.red_packet_select_item;
    }

    public List<StockBuyRedBag> getSelect() {
        List<StockBuyRedBag> select = new ArrayList<>();
        for (StockBuyRedBag stockBuyRedBag : dataList) {
            if (stockBuyRedBag.isSelect()) {
                select.add(stockBuyRedBag);
            }
        }
        return select;
    }
}
