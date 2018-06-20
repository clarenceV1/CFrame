package com.cai.work.bean.respond;

import com.cai.work.bean.WithdrawalDetail;

public class WithdrawalDetailRespond extends BaseRespond{
    private WithdrawalDetail data;

    public WithdrawalDetail getData() {
        return data;
    }

    public void setData(WithdrawalDetail data) {
        this.data = data;
    }
}
