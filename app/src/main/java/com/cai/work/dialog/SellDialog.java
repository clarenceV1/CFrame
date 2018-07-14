package com.cai.work.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cai.framework.utils.ViewHolder;
import com.cai.framework.widget.dialog.GodDialog;
import com.cai.work.R;
import com.cai.work.bean.StockHold;
import com.cai.work.ui.main.fragment.MainHoldPresenter;
import com.example.clarence.utillibrary.DeviceUtils;

public class SellDialog extends Dialog {
    Context context;
    StockHold mStockHold;
    View contentView;
    MainHoldPresenter mPresenter;

    public SellDialog(Context context, StockHold stockHold, MainHoldPresenter presenter) {
        super(context, R.style.Dialog);
        this.context = context;
        this.mPresenter = presenter;
        this.mStockHold = stockHold;
        contentView = LayoutInflater.from(context).inflate(R.layout.sell_dialog2, null);
        ViewHolder.getTextView(contentView, R.id.tvTitle).setText(context.getResources().getString(R.string.sell_dialog_title));
        ViewHolder.getTextView(contentView, R.id.tvStockName).setText(stockHold.getStockName());
        ViewHolder.getTextView(contentView, R.id.tvMoney).setText(stockHold.getPrincipal());
        ViewHolder.getTextView(contentView, R.id.tvTime).setText(stockHold.getHoldTime());
        ViewHolder.getTextView(contentView, R.id.tvNum).setText(stockHold.getDealAmount() + "股");
        ViewHolder.getTextView(contentView, R.id.tvPrice).setText(stockHold.getMarketPrice() + "元");

        ViewHolder.getView(contentView, R.id.btn_dialog_ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.checkSell(mStockHold.getId() + "", mStockHold.getMarketPrice(), mStockHold.getStockCode());
                SellDialog.this.dismiss();
            }
        });
        ViewHolder.getView(contentView, R.id.btn_dialog_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SellDialog.this.dismiss();
            }
        });
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(DeviceUtils.getScreenWidth(context), DeviceUtils.getScreenHeight(context));
        setContentView(contentView, layoutParams);

        setCanceledOnTouchOutside(false);
        setCancelable(false);
    }
}
