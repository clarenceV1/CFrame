package com.cai.work.ui.invite;

import android.content.Context;
import android.view.View;

import com.cai.framework.base.GodBaseAdapter;
import com.cai.framework.bean.CBaseData;
import com.cai.framework.utils.ViewHolder;
import com.cai.work.R;
import com.cai.work.bean.InviteOne;
import com.example.clarence.utillibrary.StringUtils;

import java.util.List;

public class MyInviteAdapter extends GodBaseAdapter {

    public MyInviteAdapter(Context context, List data) {
        super(context, data);
    }

    @Override
    public void initItemView(View convertView, CBaseData itemData, int position) {
        if (itemData instanceof InviteOne) {
            InviteOne inviteOne = (InviteOne) itemData;
            ViewHolder.getTextView(convertView, R.id.tvNum).setText(position + "");
            ViewHolder.getTextView(convertView, R.id.tvInviteUserName).setText(StringUtils.encryptName(inviteOne.getRealName()));
            ViewHolder.getTextView(convertView, R.id.tvMobile).setText(StringUtils.encryptMobile(inviteOne.getUserName()));
            ViewHolder.getTextView(convertView, R.id.tvData).setText(inviteOne.getRegisterDate());
        }
    }

    @Override
    protected int getItemLayout() {
        return R.layout.my_invite_item;
    }
}
