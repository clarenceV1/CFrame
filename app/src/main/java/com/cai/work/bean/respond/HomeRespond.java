package com.cai.work.bean.respond;

import com.cai.work.bean.home.HomeItemData;

public class HomeRespond extends BaseRespond {

    private HomeItemData data;

    public HomeItemData getData() {
        return data;
    }

    public void setData(HomeItemData data) {
        this.data = data;
    }
}
