package com.cai.work.ui.main;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cai.work.R;
import com.cai.work.ui.main.fragment.MainHoldFragment;
import com.cai.work.ui.main.fragment.MainHomeFragment;
import com.cai.work.ui.main.fragment.MainMineFragment;
import com.cai.work.ui.main.fragment.MainServiceFragment;
import com.cai.work.ui.main.fragment.MainTradeFragment;

public class MainTabView {
    private LinearLayout view;
    private TextView tvTittle;
    private ImageView imgTitle;
    private int position;
    private String fragmentName;
    boolean isSelected;//是否被选中

    private static int[] imageResorcesIds = new int[]{R.drawable.tab_main_nor, R.drawable.tab_consult_nor, R.drawable.tab_deal_nor, R.drawable.tab_position_nor, R.drawable.tab_mine_nor};
    private static int[] titleResorcesIds = new int[]{R.string.main_tab_title_1, R.string.main_tab_title_2, R.string.main_tab_title_3, R.string.main_tab_title_4, R.string.main_tab_title_5};
    private static int[] imageResorcesIds_selected = new int[]{R.drawable.tab_main_sel, R.drawable.tab_consult_sel, R.drawable.tab_deal_sel, R.drawable.tab_position_sel, R.drawable.tab_mine_sel};

    public static OnTabClickListener onTabClickListener;

    public interface OnTabClickListener {
        boolean onClick(View view, int position, String fragmentName);
    }

    private MainTabView(View view, int position) {
        this.view = (LinearLayout) view;
        this.position = position;
        init();
    }

    private void init() {
        imgTitle = (ImageView) view.getChildAt(0);
        tvTittle = (TextView) view.getChildAt(1);

        imgTitle.setImageResource(imageResorcesIds[position]);
        tvTittle.setText(titleResorcesIds[position]);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isSwitchTab = true;
                if (onTabClickListener != null) {
                    isSwitchTab = onTabClickListener.onClick(view, position, fragmentName);
                }
                if (isSwitchTab) {
                    setSelected();
                }
            }
        });
        setFragmentName();
    }

    public String getFragmentName() {
        return fragmentName;
    }

    private void setFragmentName() {
        switch (position) {
            case 0:
                fragmentName = MainHomeFragment.class.getName();
                break;
            case 1:
                fragmentName = MainServiceFragment.class.getName();
                break;
            case 2:
                fragmentName = MainTradeFragment.class.getName();
                break;
            case 3:
                fragmentName = MainHoldFragment.class.getName();
                break;
            case 4:
                fragmentName = MainMineFragment.class.getName();
                break;
        }
    }

    private void setSelected() {
        this.isSelected = false;
        this.imgTitle.setImageResource(imageResorcesIds_selected[position]);
        this.tvTittle.setTextColor(view.getResources().getColor(R.color.ys_219_183_108));
    }

    public void reset() {
        this.isSelected = false;
        this.imgTitle.setImageResource(imageResorcesIds[position]);
        this.tvTittle.setTextColor(view.getResources().getColor(R.color.ys_160_161_161));
    }

    public static MainTabView creatTabView(View view, int position) {
        return new MainTabView(view, position);
    }

    public static void setOnTabClickListener(OnTabClickListener onTabClickListener) {
        MainTabView.onTabClickListener = onTabClickListener;
    }
}
