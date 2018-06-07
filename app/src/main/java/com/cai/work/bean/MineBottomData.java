package com.cai.work.bean;

import com.cai.work.ui.main.fragment.MainMineAdapter;

public class MineBottomData implements IRecycleViewBaseData {
    @Override
    public int getLayoutType() {
        return MainMineAdapter.BOTTOM_VIEW;
    }
}
