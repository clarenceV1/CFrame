package com.cai.work.ui.main;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cai.work.R;

import javax.inject.Inject;

public class TabManager {

    @Inject
    public TabManager() {
    }

    public int[] imageResorcesIds = new int[]{R.drawable.down, R.drawable.down, R.drawable.down, R.drawable.down, R.drawable.down};
    public int[] titleResorcesIds = new int[]{R.string.main_tab_title_1, R.string.main_tab_title_2, R.string.main_tab_title_3, R.string.main_tab_title_4, R.string.main_tab_title_5};

    public View getTabView(Context context, final int position, final ViewPager viewPager) {
        View view = LayoutInflater.from(context).inflate(R.layout.main_tab_item, null);
        TextView tvTittle = (TextView) view.findViewById(R.id.tvTittle);
        ImageView imgTitle = (ImageView) view.findViewById(R.id.mImageView);
        imgTitle.setImageResource(imageResorcesIds[position]);
        tvTittle.setText(titleResorcesIds[position]);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPager.setCurrentItem(position);
            }
        });
        return view;
    }

    public void addTab(Context context, TabLayout tabLayout, ViewPager viewPager) {
        for (int i = 0; i < imageResorcesIds.length; i++) {
            tabLayout.getTabAt(i).setCustomView(getTabView(context, i, viewPager));
        }
    }

    public void changeTabStatus(TabLayout.Tab tab, boolean selected) {
        View view = tab.getCustomView();
//        ImageView imgTitle = (ImageView) view.findViewById(R.id.img_title);
//        TextView txtTitle = (TextView) view.findViewById(R.id.txt_title);
//        imgTitle.setVisibility(View.VISIBLE);
//        if (selected) {
//            txtTitle.setTextColor(Color.parseColor("#0EA73C"));
//        } else {
//            txtTitle.setTextColor(Color.parseColor("#7f7f7f"));
//            imgTitle.setVisibility(View.INVISIBLE);
//        }
    }
}
