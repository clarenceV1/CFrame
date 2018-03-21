package com.cai.work.ui.adapter;

import android.content.Context;
import android.view.View;

import com.cai.framework.base.GodBaseAdapter;
import com.cai.framework.bean.CBaseData;
import com.cai.framework.utils.ViewHolder;
import com.cai.work.R;
import com.cai.work.bean.ScrollData;

import java.util.List;

/**
 * Created by clarence on 2018/1/22.
 */

public class ScrollAdapter extends GodBaseAdapter {

    public ScrollAdapter(Context context, List<ScrollData> datas) {
        super(context, datas);
    }

    @Override
    public void initItemView(View convertView, CBaseData itemData, int position) {
        ScrollData scrollData = (ScrollData) itemData;
        ViewHolder.getTextView(convertView, R.id.itemName).setText(scrollData.getName());
    }

    @Override
    protected int getItemLayout() {
        return R.layout.scroll_item;
    }
}
