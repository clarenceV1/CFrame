package com.cai.work.ui.main;

import android.view.View;

import com.cai.work.R;
import com.cai.work.widget.TabItem;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class TabManager {

    int[] imageNormals = new int[]{R.drawable.tabbar_btn_candy, R.drawable.tabbar_btn_discovery, R.drawable.tabbar_btn_me};
    int[] imageSelects = new int[]{R.drawable.tabbar_btn_candy_active, R.drawable.tabbar_btn_discovery_active, R.drawable.tabbar_btn_me_active};
    int[] tabNames = new int[]{R.string.candy, R.string.discover, R.string.mine};
    int[] tabNameColors = new int[]{R.color.gray_757575, R.color.blue_C61BFD};


    List<TabItem> tabItemList = new ArrayList<>();
    int selectPosition = 0;
    TabItem.TabClickListener tabClickListener;

    @Inject
    public TabManager() {
    }

    public void initTab(TabItem... items) {
        if (items == null || items.length == 0) {
            return;
        }
        for (int i = 0; i < items.length; i++) {
            tabItemList.add(items[i]);
        }
        initTab();
    }

    public void setTabClickListener(TabItem.TabClickListener tabClickListener) {
        this.tabClickListener = tabClickListener;
    }

    public void initTab() {
        TabItem tabItem = null;
        for (int i = 0; i < tabItemList.size(); i++) {
            tabItem = tabItemList.get(i);
            tabItem.setPosition(i);
            tabItem.initTab(imageNormals[i], imageSelects[i], tabNameColors[0], tabNameColors[1], tabNames[i]);
            if (i == selectPosition) {
                tabItem.setSelected();
            } else {
                tabItem.setNormal();
            }
            tabItem.setTabClickListener(new TabItem.TabClickListener() {
                @Override
                public void clickListener(TabItem view, int position) {
                    if (selectPosition == position) {
                        return;
                    }
                    tabItemList.get(selectPosition).setNormal();
                    tabItemList.get(position).setSelected();
                    selectPosition = position;
                    if (tabClickListener != null) {
                        tabClickListener.clickListener(view, position);
                    }
                }
            });
        }
    }
}
