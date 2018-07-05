package com.cai.work.ui.bank;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.cai.framework.base.GodBaseAdapter;
import com.cai.framework.bean.CBaseData;
import com.cai.framework.utils.ViewHolder;
import com.cai.work.R;
import com.cai.work.bean.BankCard;
import com.example.clarence.imageloaderlibrary.GlideCircleTransform;
import com.example.clarence.imageloaderlibrary.ILoadImage;
import com.example.clarence.imageloaderlibrary.ILoadImageParams;
import com.example.clarence.imageloaderlibrary.ImageForGlideParams;
import com.example.clarence.utillibrary.DimensUtils;

import java.util.ArrayList;
import java.util.List;

class BankCardListAdapter extends GodBaseAdapter {

    ILoadImage imageLoader;
    int dp10;

    public BankCardListAdapter(Context context, ILoadImage imageLoader) {
        super(context, new ArrayList());
        this.imageLoader = imageLoader;
        dp10 = DimensUtils.px2dp(context, 30);
    }

    public void update(List data) {
        if (data != null) {
            dataList.clear();
            dataList.addAll(data);
            notifyDataSetChanged();
        }
    }

    @Override
    public void initItemView(View convertView, CBaseData itemData, int position) {
        if (itemData != null && itemData instanceof BankCard) {
            BankCard bank = (BankCard) itemData;

            ImageView ivBankIcon = ViewHolder.getImageView(convertView, R.id.ivBankIcon);
            ILoadImageParams imageParams = new ImageForGlideParams.Builder().url("http:" + bank.getmImageUrl())
                    .transformation(new GlideCircleTransform(context)).build();
            imageParams.setImageView(ivBankIcon);
            imageLoader.loadImage(context, imageParams);

            ViewHolder.getTextView(convertView, R.id.tvBankName).setText(bank.getBankName());
            ViewHolder.getTextView(convertView, R.id.tvBankType).setText(context.getResources().getString(R.string.bank_type));
            if (!TextUtils.isEmpty(bank.getCardNo()) && bank.getCardNo().length() > 4) {
                String no = bank.getCardNo().substring(bank.getCardNo().length() - 4);
                ViewHolder.getTextView(convertView, R.id.tvBankNum).setText("****  ****  ****  " + no);
                ViewHolder.getTextView(convertView, R.id.tvBankNum).setVisibility(View.VISIBLE);
            } else {
                ViewHolder.getTextView(convertView, R.id.tvBankNum).setVisibility(View.GONE);
            }
            setBg(ViewHolder.getView(convertView, R.id.llContain), position);
        }
    }

    public void setBg(View view, int position) {

        float[] outerRadii = {dp10, dp10, dp10, dp10, dp10, dp10, dp10, dp10};//外矩形 左上、右上、右下、左下的圆角半径
        RoundRectShape roundRectShape = new RoundRectShape(outerRadii, null, null);

        ShapeDrawable drawable = new ShapeDrawable(roundRectShape);
        if (position % 2 == 0) {
            drawable.getPaint().setColor(context.getResources().getColor(R.color.ys_59_95_169));
        } else {
            drawable.getPaint().setColor(context.getResources().getColor(R.color.ys_128_94_131));
        }

        drawable.getPaint().setAntiAlias(true);
        drawable.getPaint().setStyle(Paint.Style.FILL);
        view.setBackground(drawable);
    }

    @Override
    protected int getItemLayout() {
        return R.layout.bank_card_list_item;
    }
}
