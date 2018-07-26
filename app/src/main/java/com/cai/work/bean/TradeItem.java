package com.cai.work.bean;

public class TradeItem {
//    data.gn_contract.mk_price	当前价格	number
//    data.gn_contract.zdfu	涨跌幅	number
//    data.gn_contract.handMoney	保证金	number
//    data.gn_contract.amount	购买手数	number
//    data.gn_contract.money	持仓盈亏	number
//    data.gn_contract.contractCode	合约代码	string
//    data.gn_contract.contractName	合约名称
//      "shortCode": "SC",
//              "color": "85CBBB"

    private String mk_price;
    private String zdfu;
    private int isTrade;
    private String handMoney;
    private String amount;
    private String money;
    private String contractCode;
    private String contractName;
    private String shortCode;
    private String color;
    private String remark;//A股才有   "remark": "1000元起即可交易",

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

    public String getMk_price() {
        return mk_price;
    }

    public void setMk_price(String mk_price) {
        this.mk_price = mk_price;
    }

    public String getZdfu() {
        return zdfu;
    }

    public void setZdfu(String zdfu) {
        this.zdfu = zdfu;
    }

    public String getHandMoney() {
        return handMoney;
    }

    public void setHandMoney(String handMoney) {
        this.handMoney = handMoney;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

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
}
