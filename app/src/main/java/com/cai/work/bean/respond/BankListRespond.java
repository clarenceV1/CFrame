package com.cai.work.bean.respond;

import com.cai.work.bean.Bank;

import java.util.List;

public class BankListRespond extends BaseRespond {
    private List<Bank> data;

    public List<Bank> getData() {
        return data;
    }

    public void setData(List<Bank> data) {
        this.data = data;
    }
}
