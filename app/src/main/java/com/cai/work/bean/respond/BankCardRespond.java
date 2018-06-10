package com.cai.work.bean.respond;

import com.cai.work.bean.BankCard;

import java.util.List;

public class BankCardRespond extends BaseRespond {
    private List<BankCard> data;

    public List<BankCard> getData() {
        return data;
    }

    public void setData(List<BankCard> data) {
        this.data = data;
    }
}
