package com.cai.work.ui.message;

import android.content.Context;
import android.view.View;

import com.cai.framework.base.GodBaseAdapter;
import com.cai.framework.bean.CBaseData;
import com.cai.framework.utils.ViewHolder;
import com.cai.work.R;
import com.cai.work.bean.FundDetailItem;
import com.cai.work.bean.MessageItem;

import java.util.ArrayList;
import java.util.List;

class MessageAdapter extends GodBaseAdapter {
    MessagePresenter presenter;

    public MessageAdapter(Context context, MessagePresenter presenter) {
        super(context, new ArrayList());
        this.presenter = presenter;
    }

    public void update(List data) {
        if (data != null) {
            dataList.addAll(data);
            notifyDataSetChanged();
        }
    }

    public void cleanAll() {
        if (dataList != null) {
            dataList.clear();
            notifyDataSetChanged();
        }
    }

    @Override
    public void initItemView(final View convertView, CBaseData itemData, int position) {
        if (itemData != null && itemData instanceof MessageItem) {
            final MessageItem messageItem = (MessageItem) itemData;
            ViewHolder.getTextView(convertView, R.id.tvMsgTitile).setText("服务端没提供");
            if (messageItem.getReadType() == 1) { //1已读2未读
                ViewHolder.getImageView(convertView, R.id.ivNew).setVisibility(View.VISIBLE);
            } else {
                ViewHolder.getImageView(convertView, R.id.ivNew).setVisibility(View.GONE);
            }
            ViewHolder.getTextView(convertView, R.id.tvMsgContent).setText(messageItem.getContent());
            ViewHolder.getTextView(convertView, R.id.tvMsgDate).setText(messageItem.getCreateTime());
            ViewHolder.getButton(convertView, R.id.tvDelete).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    presenter.deleteMessage(messageItem.getId());
                    dataList.remove(messageItem);
                    notifyDataSetChanged();
                }
            });
            if (messageItem.isShowMsgContent()) {
                ViewHolder.getView(convertView, R.id.rlContent).setVisibility(View.VISIBLE);
            } else {
                ViewHolder.getView(convertView, R.id.rlContent).setVisibility(View.GONE);
            }
            ViewHolder.getImageView(convertView, R.id.ivSwitch).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (messageItem.isShowMsgContent()) {
                        messageItem.setShowMsgContent(false);
                        ViewHolder.getView(convertView, R.id.rlContent).setVisibility(View.GONE);
                    } else {
                        messageItem.setShowMsgContent(true);
                        ViewHolder.getView(convertView, R.id.rlContent).setVisibility(View.VISIBLE);
                    }
                }
            });
        }
    }

    @Override
    protected int getItemLayout() {
        return R.layout.message_detail_item;
    }
}
