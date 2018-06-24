package com.cai.work.bean.respond;

import com.cai.work.bean.Stock;

import java.util.List;

public class StockListRespond extends BaseRespond{
    private List<Stock> data;

    public List<Stock> getData() {
        return data;
    }

    public void setData(List<Stock> data) {
        this.data = data;
    }
}
