package com.cai.work.bean;

import com.cai.framework.bean.CBaseData;

public class ForwardHold implements CBaseData{
    //    "id": "148",
//                "orderNo": "QHJY1807061339191586",
//                "openData": "2018-07-06",
//                "openTime": "13:39:19",
//                "contractName": "铁矿石",
//                "contractCode": "i1809",
//                "openPrice": "458.5",
//                "buyWay": "1",
//                "buyWayText": "买涨",
//                "buyAmount": "1",
//                "mk_price": "458.5",
//                "ykMoney": "0.00",
//                "bond": "600",
//                "zsMoney": "500",
//                "zyMoney": "800",
//                "isClosing": "2",
//                "btnText": "平仓"
    private int id;
    private String orderNo;
    private String openData;
    private String openTime;
    private String contractName;
    private String contractCode;
    private String openPrice;
    private String buyWay;
    private String buyWayText;
    private String buyAmount;
    private String mk_price;
    private String ykMoney;
    private String bond;
    private String zsMoney;
    private String zyMoney;
    private String isClosing;
    private String btnText;

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

    public String getOpenData() {
        return openData;
    }

    public void setOpenData(String openData) {
        this.openData = openData;
    }

    public String getOpenTime() {
        return openTime;
    }

    public void setOpenTime(String openTime) {
        this.openTime = openTime;
    }

    public String getContractName() {
        return contractName;
    }

    public void setContractName(String contractName) {
        this.contractName = contractName;
    }

    public String getContractCode() {
        return contractCode;
    }

    public void setContractCode(String contractCode) {
        this.contractCode = contractCode;
    }

    public String getOpenPrice() {
        return openPrice;
    }

    public void setOpenPrice(String openPrice) {
        this.openPrice = openPrice;
    }

    public String getBuyWay() {
        return buyWay;
    }

    public void setBuyWay(String buyWay) {
        this.buyWay = buyWay;
    }

    public String getBuyWayText() {
        return buyWayText;
    }

    public void setBuyWayText(String buyWayText) {
        this.buyWayText = buyWayText;
    }

    public String getBuyAmount() {
        return buyAmount;
    }

    public void setBuyAmount(String buyAmount) {
        this.buyAmount = buyAmount;
    }

    public String getMk_price() {
        return mk_price;
    }

    public void setMk_price(String mk_price) {
        this.mk_price = mk_price;
    }

    public String getYkMoney() {
        return ykMoney;
    }

    public void setYkMoney(String ykMoney) {
        this.ykMoney = ykMoney;
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

    public String getIsClosing() {
        return isClosing;
    }

    public void setIsClosing(String isClosing) {
        this.isClosing = isClosing;
    }

    public String getBtnText() {
        return btnText;
    }

    public void setBtnText(String btnText) {
        this.btnText = btnText;
    }
}
