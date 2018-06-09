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

    private static int[] imageResorcesIds = new int[]{R.drawable.ic_launcher, R.drawable.ic_launcher, R.drawable.ic_launcher, R.drawable.ic_launcher, R.drawable.ic_launcher};
    private static int[] titleResorcesIds = new int[]{R.string.main_tab_title_1, R.string.main_tab_title_2, R.string.main_tab_title_3, R.string.main_tab_title_4, R.string.main_tab_title_5};

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
                    refreshTabView();
                }
            }
        });
        setFragmentName();
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

    private void refreshTabView() {

    }

    public static MainTabView creatTabView(View view, int position) {
        return new MainTabView(view, position);
    }

    public static void setOnTabClickListener(OnTabClickListener onTabClickListener) {
        MainTabView.onTabClickListener = onTabClickListener;
    }
}
