package com.cai.work.ui.news;

import android.content.Context;
import android.view.View;

import com.cai.framework.base.GodBaseAdapter;
import com.cai.framework.bean.CBaseData;
import com.cai.framework.utils.ViewHolder;
import com.cai.work.R;
import com.cai.work.bean.FundDetailItem;
import com.cai.work.bean.News;

import java.util.ArrayList;
import java.util.List;

class NewsAdapter extends GodBaseAdapter {

    public NewsAdapter(Context context) {
        super(context, new ArrayList());
    }

    public void update(List data) {
        if (data != null) {
            dataList.addAll(data);
            notifyDataSetChanged();
        }
    }

    @Override
    public void initItemView(View convertView, CBaseData itemData, int position) {
        if (itemData != null && itemData instanceof FundDetailItem) {
            News news = (News) itemData;
            ViewHolder.getTextView(convertView, R.id.tvNewsTitle).setText(news.getTitle());
            ViewHolder.getTextView(convertView, R.id.tvDate).setText(news.getCreateDate());
            ViewHolder.getTextView(convertView, R.id.tvContent).setText(news.getTitle());
        }
    }

    @Override
    protected int getItemLayout() {
        return R.layout.news_item;
    }
}
