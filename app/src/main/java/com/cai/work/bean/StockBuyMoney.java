package com.cai.work.bean;

import com.cai.framework.bean.CBaseData;

public class StockBuyMoney implements CBaseData {
    private float time;//倍率
    private int type;//1:买入金额，2:止盈，3，止损，4，保证金,0持仓时间

    private String txt;

    public String getTxt() {
        return txt;
    }

    public void setTxt(String txt) {
        this.txt = txt;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public float getTime() {
        return time;
    }

    public void setTime(float time) {
        this.time = time;
    }
}
