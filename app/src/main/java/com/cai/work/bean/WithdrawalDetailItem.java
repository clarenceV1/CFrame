package com.cai.work.bean;

import com.cai.framework.bean.CBaseData;

public class WithdrawalDetailItem implements CBaseData{
    /*    "orderDate": "2018-06-19 18:03:27",
                "amount": "0.32",
                "orderState": "1"*/
    private String orderDate;
    private String amount;
    private int orderState;

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public int getOrderState() {
        return orderState;
    }

    public void setOrderState(int orderState) {
        this.orderState = orderState;
    }
}
