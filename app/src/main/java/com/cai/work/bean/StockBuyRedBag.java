package com.cai.work.bean;

import com.cai.framework.bean.CBaseData;

import java.io.Serializable;

public class StockBuyRedBag implements CBaseData,Serializable{
    //    data.redBags.id	红包id	string
//    data.redBags.parValue	红包金额	string
//    data.redBags.failureTime
    private String id;
    private String parValue;
    private String failureTime;
    private boolean isSelect;

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getParValue() {
        return parValue;
    }

    public void setParValue(String parValue) {
        this.parValue = parValue;
    }

    public String getFailureTime() {
        return failureTime;
    }

    public void setFailureTime(String failureTime) {
        this.failureTime = failureTime;
    }
}
