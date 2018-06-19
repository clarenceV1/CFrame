package com.cai.work.bean.respond;

import com.cai.work.bean.Trade;

import java.util.List;

public class TradeRespond extends BaseRespond{
    private List<Trade> data;

    public List<Trade> getData() {
        return data;
    }

    public void setData(List<Trade> data) {
        this.data = data;
    }
}
