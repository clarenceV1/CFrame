package com.cai.work.bean;

import com.cai.work.ui.main.fragment.MainMineAdapter;

public class MineListData implements IRecycleViewBaseData {
    private int itemIcon;
    private int itemName;

    public int getItemIcon() {
        return itemIcon;
    }

    public void setItemIcon(int itemIcon) {
        this.itemIcon = itemIcon;
    }

    public int getItemName() {
        return itemName;
    }

    public void setItemName(int itemName) {
        this.itemName = itemName;
    }

    @Override
    public int getLayoutType() {
        return MainMineAdapter.LIST_VIEW;
    }
}
