package com.cai.work.ui.redPacket;

import android.content.Context;
import android.view.View;

import com.cai.framework.base.GodBaseAdapter;
import com.cai.framework.bean.CBaseData;
import com.cai.framework.utils.ViewHolder;
import com.cai.work.R;
import com.cai.work.bean.RedPacketItem;
import com.cai.work.bean.StockBuyRedBag;

import java.util.ArrayList;
import java.util.List;

class RedPacketSelectAdapter extends GodBaseAdapter<StockBuyRedBag> {

    public RedPacketSelectAdapter(Context context) {
        super(context, new ArrayList());
    }

    public void update(List data) {
        if (data != null) {
            dataList.addAll(data);
            notifyDataSetChanged();
        }
    }

    @Override
    public void initItemView(final View convertView, StockBuyRedBag redPacketItem, int position) {

        ViewHolder.getTextView(convertView, R.id.tvParValue).setText(redPacketItem.getParValue());
    }

    @Override
    protected int getItemLayout() {
        return R.layout.red_packet_item;
    }
}
