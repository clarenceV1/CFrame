package com.cai.work.ui.forward;

import android.content.Context;
import android.view.View;

import com.cai.framework.base.GodBaseAdapter;
import com.cai.framework.bean.CBaseData;
import com.cai.framework.utils.ViewHolder;
import com.cai.work.R;
import com.cai.work.bean.Forward;

import java.util.ArrayList;
import java.util.List;

public class ForwardAdapter extends GodBaseAdapter {

    public ForwardAdapter(Context context) {
        super(context, new ArrayList());
    }

    @Override
    public void initItemView(View convertView, CBaseData itemData, int position) {
        if (itemData != null && itemData instanceof Forward) {
            Forward forward = (Forward) itemData;
            ViewHolder.getTextView(convertView, R.id.tvForwardName).setText(forward.getName());
        }
    }

    @Override
    protected int getItemLayout() {
        return R.layout.forward_item;
    }

    public void update(List dataList) {
        this.dataList.clear();
        this.dataList.addAll(dataList);
        notifyDataSetChanged();
    }
}
