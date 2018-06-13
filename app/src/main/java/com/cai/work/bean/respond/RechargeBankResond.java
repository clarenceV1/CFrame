package com.cai.work.bean.respond;

import com.cai.work.bean.RechargeBank;

import java.util.List;

public class RechargeBankResond extends BaseRespond{
    private List<RechargeBank> data;

    public List<RechargeBank> getData() {
        return data;
    }

    public void setData(List<RechargeBank> data) {
        this.data = data;
    }
}
