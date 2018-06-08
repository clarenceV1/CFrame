package com.cai.work.ui.main.fragment;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cai.framework.base.GodBaseAdapter;
import com.cai.framework.bean.CBaseData;
import com.cai.framework.utils.ViewHolder;
import com.cai.work.R;
import com.cai.work.bean.home.HomeRangeData;

import java.util.List;

public class HomeRangeAdapter extends GodBaseAdapter {
    int[] rangeImage = new int[]{R.drawable.ic_launcher, R.drawable.ic_launcher, R.drawable.ic_launcher};

    public HomeRangeAdapter(Context context, List data) {
        super(context, data);
    }

    @Override
    public void initItemView(View convertView, CBaseData itemData, int position) {
        if (itemData instanceof HomeRangeData) {
            HomeRangeData rangeData = (HomeRangeData) itemData;
            ImageView imageView = ViewHolder.getImageView(convertView, R.id.imgRange);
            TextView tvRange = ViewHolder.getTextView(convertView, R.id.tvRange);
            if (position < 3) {
                imageView.setBackgroundResource(rangeImage[position]);
                imageView.setVisibility(View.VISIBLE);
                tvRange.setVisibility(View.GONE);
            } else {
                imageView.setVisibility(View.GONE);
                tvRange.setVisibility(View.VISIBLE);
                tvRange.setText(position + "");
            }
            ViewHolder.getTextView(convertView, R.id.tvAccount).setText(rangeData.getUser());
            ViewHolder.getTextView(convertView, R.id.tvContent).setText("操盘金额" + rangeData.getPrincipal());
            ViewHolder.getTextView(convertView, R.id.tvDate).setText(rangeData.getBuyWTDate());
        }
    }

    @Override
    public int getCount() {
        if (dataList == null) {
            return 0;
        } else if (dataList.size() > 5) {
            return 5;
        } else {
           return dataList.size();
        }
    }

    @Override
    protected int getItemLayout() {
        return R.layout.home_range_item;
    }
}
