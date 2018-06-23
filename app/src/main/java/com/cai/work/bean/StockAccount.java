package com.cai.work.bean;

import com.cai.framework.bean.CBaseData;

public class StockAccount implements CBaseData{
//    "id": 8181,
//            "orderNo": "1806071057174278",
//            "stockCode": "600000",
//            "stockName": "浦发银行",
//            "marketType": "2",
//            "principal": "10000",
//            "bond": "1000.00",
//            "overallCost": "49.00",
//            "dealAmount": "900",
//            "buyWTPrice": "10.64",
//            "buyPrice": "10.64",
//            "buyWTDate": "2018-06-07 10:57:17",
//            "buyDealDate": "2018-06-07 10:57:17",
//            "sellWTPrice": "10.39",
//            "sellPrice": "10.39",
//            "sellWTDate": "2018-06-08 14:10:11",
//            "sellDealDate": "2018-06-08 14:10:11",
//            "zyMoney": "1000.00",
//            "zsMoney": "800.00",
//            "ykMoney": "-225.00",
//            "dyTimes": "0",
//            "state": "4",
//            "approveState": "1",
//            "remark": "",
//            "yk_money": -225,
//            "approveStateText": "手动平仓"

    private int id;
    private String orderNo;
    private String stockCode;
    private String stockName;
    private String marketType;
    private String principal;
    private String bond;
    private String overallCost;
    private String dealAmount;
    private String buyWTPrice;
    private String buyPrice;
    private String buyWTDate;
    private String buyDealDate;
    private String sellWTPrice;
    private String sellPrice;
    private String sellWTDate;
    private String sellDealDate;
    private String zyMoney;
    private String zsMoney;
    private String ykMoney;
    private String dyTimes;
    private String state;
    private String approveState;
    private String remark;
    private String yk_money;
    private String approveStateText;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getStockCode() {
        return stockCode;
    }

    public void setStockCode(String stockCode) {
        this.stockCode = stockCode;
    }

    public String getStockName() {
        return stockName;
    }

    public void setStockName(String stockName) {
        this.stockName = stockName;
    }

    public String getMarketType() {
        return marketType;
    }

    public void setMarketType(String marketType) {
        this.marketType = marketType;
    }

    public String getPrincipal() {
        return principal;
    }

    public void setPrincipal(String principal) {
        this.principal = principal;
    }

    public String getBond() {
        return bond;
    }

    public void setBond(String bond) {
        this.bond = bond;
    }

    public String getOverallCost() {
        return overallCost;
    }

    public void setOverallCost(String overallCost) {
        this.overallCost = overallCost;
    }

    public String getDealAmount() {
        return dealAmount;
    }

    public void setDealAmount(String dealAmount) {
        this.dealAmount = dealAmount;
    }

    public String getBuyWTPrice() {
        return buyWTPrice;
    }

    public void setBuyWTPrice(String buyWTPrice) {
        this.buyWTPrice = buyWTPrice;
    }

    public String getBuyPrice() {
        return buyPrice;
    }

    public void setBuyPrice(String buyPrice) {
        this.buyPrice = buyPrice;
    }

    public String getBuyWTDate() {
        return buyWTDate;
    }

    public void setBuyWTDate(String buyWTDate) {
        this.buyWTDate = buyWTDate;
    }

    public String getBuyDealDate() {
        return buyDealDate;
    }

    public void setBuyDealDate(String buyDealDate) {
        this.buyDealDate = buyDealDate;
    }

    public String getSellWTPrice() {
        return sellWTPrice;
    }

    public void setSellWTPrice(String sellWTPrice) {
        this.sellWTPrice = sellWTPrice;
    }

    public String getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(String sellPrice) {
        this.sellPrice = sellPrice;
    }

    public String getSellWTDate() {
        return sellWTDate;
    }

    public void setSellWTDate(String sellWTDate) {
        this.sellWTDate = sellWTDate;
    }

    public String getSellDealDate() {
        return sellDealDate;
    }

    public void setSellDealDate(String sellDealDate) {
        this.sellDealDate = sellDealDate;
    }

    public String getZyMoney() {
        return zyMoney;
    }

    public void setZyMoney(String zyMoney) {
        this.zyMoney = zyMoney;
    }

    public String getZsMoney() {
        return zsMoney;
    }

    public void setZsMoney(String zsMoney) {
        this.zsMoney = zsMoney;
    }

    public String getYkMoney() {
        return ykMoney;
    }

    public void setYkMoney(String ykMoney) {
        this.ykMoney = ykMoney;
    }

    public String getDyTimes() {
        return dyTimes;
    }

    public void setDyTimes(String dyTimes) {
        this.dyTimes = dyTimes;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getApproveState() {
        return approveState;
    }

    public void setApproveState(String approveState) {
        this.approveState = approveState;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getYk_money() {
        return yk_money;
    }

    public void setYk_money(String yk_money) {
        this.yk_money = yk_money;
    }

    public String getApproveStateText() {
        return approveStateText;
    }

    public void setApproveStateText(String approveStateText) {
        this.approveStateText = approveStateText;
    }
}
