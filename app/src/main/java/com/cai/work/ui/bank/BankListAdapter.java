package com.cai.work.ui.bank;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.cai.framework.base.GodBaseAdapter;
import com.cai.framework.bean.CBaseData;
import com.cai.framework.utils.ViewHolder;
import com.cai.work.R;
import com.cai.work.bean.Bank;
import com.example.clarence.imageloaderlibrary.GlideCircleTransform;
import com.example.clarence.imageloaderlibrary.ILoadImage;
import com.example.clarence.imageloaderlibrary.ILoadImageParams;
import com.example.clarence.imageloaderlibrary.ImageForGlideParams;

import java.util.ArrayList;
import java.util.List;

class BankListAdapter extends GodBaseAdapter {

    ILoadImage imageLoader;

    public BankListAdapter(Context context, ILoadImage imageLoader) {
        super(context, new ArrayList());
        this.imageLoader = imageLoader;
    }

    public void update(List data) {
        if (data != null) {
            dataList.addAll(data);
            notifyDataSetChanged();
        }
    }

    @Override
    public void initItemView(View convertView, CBaseData itemData, int position) {
        if (itemData != null && itemData instanceof Bank) {
            Bank bank = (Bank) itemData;

            ImageView ivBankIcon = ViewHolder.getImageView(convertView, R.id.ivBankIcon);
            ILoadImageParams imageParams = new ImageForGlideParams.Builder().url("http:"+bank.getImageUrl())
                    .transformation(new GlideCircleTransform(context)).build();
            imageParams.setImageView(ivBankIcon);
            imageLoader.loadImage(context, imageParams);

            ViewHolder.getTextView(convertView, R.id.tvBankName).setText(bank.getBankName());
            ViewHolder.getTextView(convertView, R.id.tvBankType).setText(context.getResources().getString(R.string.bank_type));
            ViewHolder.getTextView(convertView, R.id.tvBankNum).setText(bank.getCardNo());
        }
    }

    @Override
    protected int getItemLayout() {
        return R.layout.bank_list_item;
    }
}
