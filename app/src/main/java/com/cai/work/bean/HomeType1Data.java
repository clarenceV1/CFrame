package com.cai.work.bean;

import java.util.List;

/**
 * 分类
 */
public class HomeType1Data implements IRecycleViewBaseData {
   private List<HomeType1DataItem> itemList;

    public List<HomeType1DataItem> getItemList() {
        return itemList;
    }

    public void setItemList(List<HomeType1DataItem> itemList) {
        this.itemList = itemList;
    }

    @Override
    public int getLayoutType() {
        return 1;
    }
}
