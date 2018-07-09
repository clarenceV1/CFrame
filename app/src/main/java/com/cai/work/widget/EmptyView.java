package com.cai.work.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cai.work.R;

public class EmptyView extends RelativeLayout {
    TextView tvNotice;
    TextView tvTryAgain;

    public EmptyView(Context context) {
        super(context);
        initView();
    }

    public EmptyView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    public EmptyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }


    private void initView() {
        LayoutInflater.from(getContext()).inflate(R.layout.empty_view, this);
        tvNotice = findViewById(R.id.tvNotice);
        tvTryAgain = findViewById(R.id.tvTryAgain);
    }

    public void setNotice(String notice) {
        tvNotice.setText(notice);
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        tvTryAgain.setOnClickListener(onClickListener);
    }
}
