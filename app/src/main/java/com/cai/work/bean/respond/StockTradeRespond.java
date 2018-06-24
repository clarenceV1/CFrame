package com.cai.work.bean.respond;

import com.cai.work.bean.StockTrade;

public class StockTradeRespond extends BaseRespond {
    private StockTrade data;

    public StockTrade getData() {
        return data;
    }

    public void setData(StockTrade data) {
        this.data = data;
    }
}
