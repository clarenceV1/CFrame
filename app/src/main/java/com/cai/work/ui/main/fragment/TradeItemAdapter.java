package com.cai.work.ui.main.fragment;

import android.content.Context;
import android.view.View;

import com.cai.framework.base.GodBaseAdapter;
import com.cai.framework.bean.CBaseData;
import com.cai.framework.utils.ViewHolder;
import com.cai.framework.widget.CircleView;
import com.cai.work.R;
import com.cai.work.bean.home.HomeNphyData;
import com.cai.work.bean.home.HomeWphyData;

import java.util.List;

public class TradeItemAdapter extends GodBaseAdapter {

    public TradeItemAdapter(Context context, List data) {
        super(context, data);
    }

    @Override
    public void initItemView(View convertView, CBaseData itemData, int position) {

    }

    @Override
    protected int getItemLayout() {
        return R.layout.trade_item;
    }
}
