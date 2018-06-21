package com.cai.work.bean.home;

import com.cai.framework.bean.CBaseData;

import java.io.Serializable;

public class HomeWphyData implements Serializable,CBaseData {
    //    data.wphy.contractCode	代码	string
//    data.wphy.contractName	名称	string
//    data.wphy.bond	保证金	number
//    data.wphy.priceTick	浮动价格	number
//    data.wphy.tradeTime	交易时间段	string
//    data.wphy.isTrade	是否交易	number
//    data.wphy.remark	备注说明	string
//    data.wphy.shortCode	简码	string
//    data.wphy.color	颜色
    private String contractCode;
    private String contractName;
    private String bond;
    private String priceTick;
    private String tradeTime;
    private int isTrade;
    private String remark;
    private String shortCode;
    private String color;

    public String getContractCode() {
        return contractCode;
    }

    public void setContractCode(String contractCode) {
        this.contractCode = contractCode;
    }

    public String getContractName() {
        return contractName;
    }

    public void setContractName(String contractName) {
        this.contractName = contractName;
    }

    public String getBond() {
        return bond;
    }

    public void setBond(String bond) {
        this.bond = bond;
    }

    public String getPriceTick() {
        return priceTick;
    }

    public void setPriceTick(String priceTick) {
        this.priceTick = priceTick;
    }

    public String getTradeTime() {
        return tradeTime;
    }

    public void setTradeTime(String tradeTime) {
        this.tradeTime = tradeTime;
    }

    public int getIsTrade() {
        return isTrade;
    }

    public void setIsTrade(int isTrade) {
        this.isTrade = isTrade;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getShortCode() {
        return shortCode;
    }

    public void setShortCode(String shortCode) {
        this.shortCode = shortCode;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

}