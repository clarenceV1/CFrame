package com.cai.work.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cai.work.R;

public class TabItem extends RelativeLayout implements View.OnClickListener {
    ImageView tvTabImage;
    TextView tvTabName;
    int imageNormal;
    int imageSelect;
    int nameColorNormal;
    int nameColorSelect;
    int tabName;
    int position;//位置
    TabClickListener tabClickListener;

    public TabItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.tab_item, this);
        tvTabImage = findViewById(R.id.tvTabImage);
        tvTabName = findViewById(R.id.tvTabName);
        setOnClickListener(this);
    }

    public void initTab(int imageNormal, int imageSelect, int nameColorNormal, int nameColorSelect, int tabName) {
        this.imageNormal = imageNormal;
        this.imageSelect = imageSelect;
        this.nameColorNormal = nameColorNormal;
        this.nameColorSelect = nameColorSelect;
        this.tabName = tabName;
        tvTabName.setText(tabName);
    }

    public void setTabClickListener(TabClickListener tabClickListener) {
        this.tabClickListener = tabClickListener;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public void setSelected() {
        tvTabImage.setBackgroundResource(imageSelect);
        tvTabName.setTextColor(getContext().getResources().getColor(nameColorSelect));
    }

    public void setNormal() {
        tvTabImage.setBackgroundResource(imageNormal);
        tvTabName.setTextColor(nameColorNormal);
    }

    @Override
    public void onClick(View v) {
        if (tabClickListener != null) {
            tabClickListener.clickListener(this, position);
        }
    }

    public interface TabClickListener {
        void clickListener(TabItem view, int position);
    }
}
