package com.cai.work.bean;

public class ForwardAccount {
//    "id": 6814,
//            "orderNo": "1806081410264304",
//            "contractCode": "HG1807",
//            "contractName": "美精铜",
//            "buyAmount": "1",
//            "buyWay": "2",
//            "openPrice": "3.2595",
//            "closePrice": "3.26",
//            "bond": "2125",
//            "zsMoney": "1925",
//            "zyMoney": "3150",
//            "closeDealDate": "2018-06-08 14:12:36",
//            "approveStateText": "反手平仓",
//            "stateText": "结算成功",
//            "ykMoney": "-87.5"
    private int id;
    private String orderNo;
    private String contractCode;
    private String buyAmount;
    private String buyWay;
    private String openPrice;
    private String closePrice;
    private String bond;
    private String zsMoney;
    private String zyMoney;
    private String closeDealDate;
    private String approveStateText;
    private String stateText;
    private String ykMoney;

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

    public String getContractCode() {
        return contractCode;
    }

    public void setContractCode(String contractCode) {
        this.contractCode = contractCode;
    }

    public String getBuyAmount() {
        return buyAmount;
    }

    public void setBuyAmount(String buyAmount) {
        this.buyAmount = buyAmount;
    }

    public String getBuyWay() {
        return buyWay;
    }

    public void setBuyWay(String buyWay) {
        this.buyWay = buyWay;
    }

    public String getOpenPrice() {
        return openPrice;
    }

    public void setOpenPrice(String openPrice) {
        this.openPrice = openPrice;
    }

    public String getClosePrice() {
        return closePrice;
    }

    public void setClosePrice(String closePrice) {
        this.closePrice = closePrice;
    }

    public String getBond() {
        return bond;
    }

    public void setBond(String bond) {
        this.bond = bond;
    }

    public String getZsMoney() {
        return zsMoney;
    }

    public void setZsMoney(String zsMoney) {
        this.zsMoney = zsMoney;
    }

    public String getZyMoney() {
        return zyMoney;
    }

    public void setZyMoney(String zyMoney) {
        this.zyMoney = zyMoney;
    }

    public String getCloseDealDate() {
        return closeDealDate;
    }

    public void setCloseDealDate(String closeDealDate) {
        this.closeDealDate = closeDealDate;
    }

    public String getApproveStateText() {
        return approveStateText;
    }

    public void setApproveStateText(String approveStateText) {
        this.approveStateText = approveStateText;
    }

    public String getStateText() {
        return stateText;
    }

    public void setStateText(String stateText) {
        this.stateText = stateText;
    }

    public String getYkMoney() {
        return ykMoney;
    }

    public void setYkMoney(String ykMoney) {
        this.ykMoney = ykMoney;
    }
}
