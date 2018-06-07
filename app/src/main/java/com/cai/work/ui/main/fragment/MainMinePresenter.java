package com.cai.work.ui.main.fragment;

import com.cai.work.R;
import com.cai.work.bean.IRecycleViewBaseData;
import com.cai.work.bean.MineBottomData;
import com.cai.work.bean.MineListData;
import com.cai.work.bean.MineTopData;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class MainMinePresenter {

    private int[] names = new int[]{
            R.string.mine_menu_title1, R.string.mine_menu_title2, R.string.mine_menu_title3,
            R.string.mine_menu_title4, R.string.mine_menu_title5, R.string.mine_menu_title6,
            R.string.mine_menu_title7, R.string.mine_menu_title8, R.string.mine_menu_title9
    };
    private int[] icons = new int[]{
            R.drawable.ic_launcher, R.drawable.ic_launcher, R.drawable.ic_launcher,
            R.drawable.ic_launcher, R.drawable.ic_launcher, R.drawable.ic_launcher,
            R.drawable.ic_launcher, R.drawable.ic_launcher, R.drawable.ic_launcher
    };

    @Inject
    public MainMinePresenter() {
    }

    public List<IRecycleViewBaseData> getDatas() {
        List<IRecycleViewBaseData> dataList = new ArrayList<>();
        MineTopData topData = new MineTopData();
        topData.setHeadIcon("http://img5.imgtn.bdimg.com/it/u=269889177,603310778&fm=27&gp=0.jpg");
        topData.setAccount("13779926287");
        topData.setMoney("1000000.00");
        dataList.add(topData);

        MineListData listData;
        for (int i = 0; i < names.length; i++) {
            listData = new MineListData();
            listData.setItemIcon(icons[i]);
            listData.setItemName(names[i]);
            dataList.add(listData);
        }
        MineBottomData bottomData = new MineBottomData();
        dataList.add(bottomData);
        return dataList;
    }
}
