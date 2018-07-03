package com.cai.work.ui.main;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import com.cai.framework.base.GodBaseAdapter;
import com.cai.framework.bean.CBaseData;
import com.cai.framework.utils.ViewHolder;
import com.cai.work.R;
import com.cai.work.bean.CandyList;
import com.example.clarence.utillibrary.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class CandyAdapter extends GodBaseAdapter {

    int[] bgIds = new int[]{R.drawable.candy_img_background1, R.drawable.candy_img_background2, R.drawable.candy_img_background3};
    int[] textColors = new int[]{Color.parseColor("#EB58FF"), Color.parseColor("#5470FF"), Color.parseColor("#4C93FF")};

    public CandyAdapter(Context context) {
        super(context, new ArrayList());
    }

    public void updateAll(List<CandyList> data) {
        dataList.clear();
        if(data!=null){
            dataList.addAll(data);
        }
        notifyDataSetChanged();
    }

    @Override
    public void initItemView(View convertView, CBaseData itemData, int position) {
        if (itemData instanceof CandyList) {
            CandyList candyList = (CandyList) itemData;
            ViewHolder.getView(convertView, R.id.itemRoot).setBackgroundResource(bgIds[position % 3]);
            TextView tvCommit = ViewHolder.getTextView(convertView, R.id.tvCommit);
            tvCommit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            if (candyList.getGive_total() > 0) {
                ViewHolder.getTextView(convertView, R.id.tvGive).setVisibility(View.VISIBLE);
                ViewHolder.getTextView(convertView, R.id.tvGive).setText("+" + StringUtils.formatNum(candyList.getGive_total()));
                tvCommit.setText(R.string.candy_receive_now);
                tvCommit.setBackgroundResource(R.drawable.candy_btn_getnow);
                tvCommit.setTextColor(Color.WHITE);
                ViewHolder.getTextView(convertView, R.id.tvTotal).setVisibility(View.GONE);
            } else {
                ViewHolder.getTextView(convertView, R.id.tvGive).setVisibility(View.GONE);
                ViewHolder.getTextView(convertView, R.id.tvTotal).setVisibility(View.VISIBLE);
                ViewHolder.getTextView(convertView, R.id.tvTotal).setText(context.getString(R.string.wallet_total, StringUtils.formatNum(candyList.getWallet_total())));
                tvCommit.setText(R.string.candy_receive_more);
                tvCommit.setTextColor(textColors[position % 3]);
                tvCommit.setBackgroundResource(R.drawable.candy_btn_getmore);
            }
        }
    }

    @Override
    protected int getItemLayout() {
        return R.layout.candy_item;
    }
}
