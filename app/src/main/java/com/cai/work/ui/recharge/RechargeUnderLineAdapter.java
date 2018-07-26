package com.cai.work.ui.recharge;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.view.View;

import com.cai.framework.base.GodBaseAdapter;
import com.cai.framework.bean.CBaseData;
import com.cai.framework.imageloaderlibrary.GlideCircleTransform;
import com.cai.framework.imageloaderlibrary.ILoadImage;
import com.cai.framework.imageloaderlibrary.ILoadImageParams;
import com.cai.framework.imageloaderlibrary.ImageForGlideParams;
import com.cai.framework.utils.ViewHolder;
import com.cai.work.R;
import com.cai.work.bean.RechargeBank;
import com.example.clarence.utillibrary.ClipBoardUtils;
import com.example.clarence.utillibrary.ToastUtils;

import java.util.ArrayList;
import java.util.List;

class RechargeUnderLineAdapter extends GodBaseAdapter {

    ILoadImage imageLoader;
    RechargeBank choose;

    public RechargeUnderLineAdapter(Context context, ILoadImage imageLoader) {
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
        if (itemData != null && itemData instanceof RechargeBank) {
            final RechargeBank rechargeBank = (RechargeBank) itemData;
            ILoadImageParams imageParams = new ImageForGlideParams.Builder()
                    .url("http:" + rechargeBank.getmImageUrl())
                    .transformation(new GlideCircleTransform(context))
                    .build();
            imageParams.setImageView(ViewHolder.getImageView(convertView, R.id.imgeBankIcon));
            imageLoader.loadImage(context, imageParams);
            if (rechargeBank.isCheck()) {
                choose = rechargeBank;
                ViewHolder.getView(convertView, R.id.imgChoose).setVisibility(View.VISIBLE);
                ViewHolder.getView(convertView, R.id.container).setBackgroundResource(R.drawable.bank_red_bg);
            } else {
                ViewHolder.getView(convertView, R.id.container).setBackgroundResource(R.drawable.bank_bg);
                ViewHolder.getView(convertView, R.id.imgChoose).setVisibility(View.GONE);
            }
            ViewHolder.getTextView(convertView, R.id.tvBankAccount).setText(rechargeBank.getOfflineAccount());
            ViewHolder.getTextView(convertView, R.id.tvBankUserName).setText(rechargeBank.getOfflineName());
            ViewHolder.getTextView(convertView, R.id.tvBankName).setText(rechargeBank.getOfflineBranch());
            ViewHolder.getTextView(convertView, R.id.tvCopyAccount).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ClipBoardUtils.copyToClipBoard(context, "账号", rechargeBank.getOfflineAccount());
                    ToastUtils.showShort(context.getResources().getString(R.string.recharge_copy_account_toast));
                }
            });
        }
    }

    @Override
    protected int getItemLayout() {
        return R.layout.recharge_underline_item;
    }

    public int getChooseId() {
        if (choose != null) {
            return choose.getId();
        }
        return -1;
    }

    public void setChoose(CBaseData choosed) {
        if (choosed == null) {
            return;
        }
        RechargeBank rechargeBank = (RechargeBank) choosed;
        if (choose != null) {
            choose.setCheck(false);
        }
        rechargeBank.setCheck(true);
        notifyDataSetChanged();
    }
}
