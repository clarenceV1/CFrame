package com.cai.work.bean;

public class StockBuyRedBag {
    //    data.redBags.id	红包id	string
//    data.redBags.parValue	红包金额	string
//    data.redBags.failureTime
    private String id;
    private String parValue;
    private String failureTime;

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
