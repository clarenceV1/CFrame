package com.meetone.work.bean.respond;

import com.meetone.work.bean.Asset;

import java.util.List;

public class AssetRespond extends BaseRespond{
    private List<Asset> data;

    public List<Asset> getData() {
        return data;
    }

    public void setData(List<Asset> data) {
        this.data = data;
    }
}
