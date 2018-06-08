package com.cai.work.bean.home;

/**
 * 分类
 */
public class HomeStockData{
  /*  data.stock	股票信息	object
    data.stock.isTrade	是否交易	number
    data.stock.remark	备注说明	string
    data.stock.bond	保证金	number
    data.stock.contractName	A股名称	string
    data.stock.shortCode	简码（A）	string
    data.stock.color	颜色代码	string
    data.stock.tradeTime	交易时间*/
    private int isTrade;
    private String remark;
    private String  bond;
    private String contractName;
    private String shortCode;
    private String color;
    private String tradeTime;

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

    public String getBond() {
        return bond;
    }

    public void setBond(String bond) {
        this.bond = bond;
    }

    public String getContractName() {
        return contractName;
    }

    public void setContractName(String contractName) {
        this.contractName = contractName;
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

    public String getTradeTime() {
        return tradeTime;
    }

    public void setTradeTime(String tradeTime) {
        this.tradeTime = tradeTime;
    }

}
