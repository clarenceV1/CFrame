package com.cai.work.ui.main;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.cai.work.ui.main.fragment.MainHoldFragment;
import com.cai.work.ui.main.fragment.MainHomeFragment;
import com.cai.work.ui.main.fragment.MainMineFragment;
import com.cai.work.ui.main.fragment.MainServiceFragment;
import com.cai.work.ui.main.fragment.MainTradeFragment;

import java.util.ArrayList;
import java.util.List;

public class MainAdapter extends FragmentPagerAdapter {

    List<Fragment> fragmentList = new ArrayList<>();

    String[] fragmentNames = new String[]{
            MainHomeFragment.class.getName(),
            MainServiceFragment.class.getName(),
            MainTradeFragment.class.getName(),
            MainHoldFragment.class.getName(),
            MainMineFragment.class.getName()};

    public MainAdapter(Context context, FragmentManager fm) {
        super(fm);
        for (String fragmentName : fragmentNames) {
            fragmentList.add(Fragment.instantiate(context, fragmentName));
        }
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }
}
