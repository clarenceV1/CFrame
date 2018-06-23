package com.cai.work.bean.respond;

import com.cai.work.bean.StockHold;

import java.util.List;

public class StockHoldRespond extends BaseRespond {
    private List<StockHold> data;

    public List<StockHold> getData() {
        return data;
    }

    public void setData(List<StockHold> data) {
        this.data = data;
    }
}
