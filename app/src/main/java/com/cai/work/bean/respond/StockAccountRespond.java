package com.cai.work.bean.respond;

import com.cai.work.bean.StockAccount;

import java.util.List;

public class StockAccountRespond extends BaseRespond{
    private List<StockAccount> data;

    public List<StockAccount> getData() {
        return data;
    }

    public void setData(List<StockAccount> data) {
        this.data = data;
    }
}
