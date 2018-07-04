package com.cai.work.ui.main;

import android.content.Context;
import android.view.View;

import com.cai.framework.base.GodBaseAdapter;
import com.cai.framework.utils.ViewHolder;
import com.cai.work.R;
import com.cai.work.bean.Invite;

import java.util.List;

public class InviteAdapter extends GodBaseAdapter<Invite> {

    public InviteAdapter(Context context, List data) {
        super(context, data);
    }

    public void update(List<Invite> datas) {
        if (datas == null || datas.size() == 0) {
            return;
        }
        dataList.clear();
        dataList.addAll(datas);
        notifyDataSetChanged();
    }

    @Override
    public void initItemView(View convertView, Invite itemData, int position) {
        ViewHolder.getTextView(convertView, R.id.tvNum).setText(itemData.getNum() + "");
        ViewHolder.getTextView(convertView, R.id.tvName).setText(itemData.getName());
    }

    @Override
    protected int getItemLayout() {
        return R.layout.mine_invite_item;
    }
}
