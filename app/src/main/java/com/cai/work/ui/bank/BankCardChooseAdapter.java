package com.cai.work.ui.bank;

import android.content.Context;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;

import com.cai.framework.base.GodBaseAdapter;
import com.cai.framework.bean.CBaseData;
import com.cai.framework.utils.ViewHolder;
import com.cai.work.R;
import com.cai.work.bean.BankCard;
import com.cai.work.bean.WithdrawalBank;
import com.example.clarence.imageloaderlibrary.GlideCircleTransform;
import com.example.clarence.imageloaderlibrary.ILoadImage;
import com.example.clarence.imageloaderlibrary.ILoadImageParams;
import com.example.clarence.imageloaderlibrary.ImageForGlideParams;

import java.util.List;

class BankCardChooseAdapter extends GodBaseAdapter {

    ILoadImage imageLoader;
    WithdrawalBank choose;

    public BankCardChooseAdapter(Context context, ILoadImage imageLoader, List data) {
        super(context, data);
        this.imageLoader = imageLoader;
    }

    @Override
    public void initItemView(View convertView, CBaseData itemData, int position) {
        if (itemData != null && itemData instanceof WithdrawalBank) {
            WithdrawalBank bank = (WithdrawalBank) itemData;

            ImageView ivBankIcon = ViewHolder.getImageView(convertView, R.id.ivBankIcon);
            ILoadImageParams imageParams = new ImageForGlideParams.Builder().url("http:" + bank.getmImageUrl())
                    .transformation(new GlideCircleTransform(context)).build();
            imageParams.setImageView(ivBankIcon);
            imageLoader.loadImage(context, imageParams);

            ViewHolder.getTextView(convertView, R.id.tvBankName).setText(bank.getBankName());
            ViewHolder.getTextView(convertView, R.id.tvBankNum).setText(bank.getSimpleCardNo());
            if (bank.isChoose()) {
                ViewHolder.getImageView(convertView, R.id.checkbox).setVisibility(View.VISIBLE);
            } else {
                ViewHolder.getImageView(convertView, R.id.checkbox).setVisibility(View.GONE);
            }
        }
    }

    @Override
    protected int getItemLayout() {
        return R.layout.bank_card_choose_item;
    }
}
