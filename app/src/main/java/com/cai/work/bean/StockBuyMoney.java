package com.cai.work.bean;

import com.cai.framework.bean.CBaseData;

public class StockBuyMoney implements CBaseData {
    private String money;
    private int type;//1:买入金额，2:止盈，3，止损，4，保证金

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }
}
