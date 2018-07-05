package com.cai.work.ui.redPacket;

import android.content.Context;
import android.view.View;

import com.cai.framework.base.GodBaseAdapter;
import com.cai.framework.bean.CBaseData;
import com.cai.framework.utils.ViewHolder;
import com.cai.work.R;
import com.cai.work.bean.RedPacketItem;

import java.util.ArrayList;
import java.util.List;

class RedPacketAdapter extends GodBaseAdapter {

    public RedPacketAdapter(Context context) {
        super(context, new ArrayList());
    }

    public void update(List data) {
        if (data != null) {
            dataList.addAll(data);
            notifyDataSetChanged();
        }
    }

    @Override
    public void initItemView(final View convertView, CBaseData itemData, int position) {
        if (itemData != null && itemData instanceof RedPacketItem) {
            final RedPacketItem redPacketItem = (RedPacketItem) itemData;
            if (redPacketItem.getStatus() == 1) { //1未使用2已使用3过期
                ViewHolder.getView(convertView,R.id.llContain).setBackgroundResource(R.drawable.hb_unused);
                ViewHolder.getTextView(convertView, R.id.tvRmb).setTextColor(context.getResources().getColor(R.color.ys_255_231_59));
                ViewHolder.getTextView(convertView, R.id.tvParValue).setTextColor(context.getResources().getColor(R.color.ys_255_231_59));
                ViewHolder.getTextView(convertView, R.id.tvDueDate).setTextColor(context.getResources().getColor(R.color.ys_204_204_204));
                ViewHolder.getTextView(convertView, R.id.tvRedText).setTextColor(context.getResources().getColor(R.color.ys_255_255_255));
                ViewHolder.getTextView(convertView, R.id.tvState).setTextColor(context.getResources().getColor(R.color.ys_255_255_255));
                ViewHolder.getTextView(convertView, R.id.tvState).setText(context.getResources().getString(R.string.red_packet_no_use));
            } else if (redPacketItem.getStatus() == 2) {
                ViewHolder.getView(convertView,R.id.llContain).setBackgroundResource(R.drawable.hb_used);
                ViewHolder.getTextView(convertView, R.id.tvRmb).setTextColor(context.getResources().getColor(R.color.ys_204_204_204));
                ViewHolder.getTextView(convertView, R.id.tvParValue).setTextColor(context.getResources().getColor(R.color.ys_204_204_204));
                ViewHolder.getTextView(convertView, R.id.tvDueDate).setTextColor(context.getResources().getColor(R.color.ys_77_77_77));
                ViewHolder.getTextView(convertView, R.id.tvRedText).setTextColor(context.getResources().getColor(R.color.ys_204_204_204));
                ViewHolder.getTextView(convertView, R.id.tvState).setTextColor(context.getResources().getColor(R.color.ys_77_77_77));
                ViewHolder.getTextView(convertView, R.id.tvState).setText(context.getResources().getString(R.string.red_packet_use));
            } else if (redPacketItem.getStatus() == 3) {
                ViewHolder.getView(convertView,R.id.llContain).setBackgroundResource(R.drawable.hb_used);
                ViewHolder.getTextView(convertView, R.id.tvRmb).setTextColor(context.getResources().getColor(R.color.ys_204_204_204));
                ViewHolder.getTextView(convertView, R.id.tvParValue).setTextColor(context.getResources().getColor(R.color.ys_204_204_204));
                ViewHolder.getTextView(convertView, R.id.tvDueDate).setTextColor(context.getResources().getColor(R.color.ys_77_77_77));
                ViewHolder.getTextView(convertView, R.id.tvRedText).setTextColor(context.getResources().getColor(R.color.ys_204_204_204));
                ViewHolder.getTextView(convertView, R.id.tvState).setTextColor(context.getResources().getColor(R.color.ys_77_77_77));
                ViewHolder.getTextView(convertView, R.id.tvState).setText(context.getResources().getString(R.string.red_packet_overdue));
            }

            ViewHolder.getTextView(convertView, R.id.tvParValue).setText(redPacketItem.getParValue());
            ViewHolder.getTextView(convertView, R.id.tvRedText).setText(redPacketItem.getRedText());
            ViewHolder.getTextView(convertView, R.id.tvDueDate).setText(redPacketItem.getDueDate());
        }
    }

    @Override
    protected int getItemLayout() {
        return R.layout.red_packet_item;
    }
}
