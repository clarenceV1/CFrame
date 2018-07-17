package com.meetone.work.bean.respond;


import com.meetone.work.bean.Discover;

import java.util.List;

public class DiscoverRespond extends BaseRespond{
    private List<Discover> data;

    public List<Discover> getData() {
        return data;
    }

    public void setData(List<Discover> data) {
        this.data = data;
    }
}
