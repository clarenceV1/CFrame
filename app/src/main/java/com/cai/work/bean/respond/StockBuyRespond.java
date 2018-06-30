package com.cai.work.bean.respond;

import com.cai.work.bean.StockBuy;

public class StockBuyRespond extends BaseRespond {
    private StockBuy data;

    public StockBuy getData() {
        return data;
    }

    public void setData(StockBuy data) {
        this.data = data;
    }
}
