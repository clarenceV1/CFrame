package com.cai.work.bean;

public class Record {
//    	"isTrade": "1",
//                "buyDate": "09:00-10:15,10:30-11:30,13:30-14:50,21:00-次日02:20",
//                "attributeType": "1",
//                "type": "1",
//                "contractCode": "ag1812",
//                "subMarketCode": "",
//                "contractName": "沪银",
//                "ruleName": null

    private int isTrade;
    private String buyDate;
    private int attributeType;
    private int type;
    private String contractCode;
    private String subMarketCode;
    private String contractName;
    private String ruleName;

    public int getIsTrade() {
        return isTrade;
    }

    public void setIsTrade(int isTrade) {
        this.isTrade = isTrade;
    }

    public String getBuyDate() {
        return buyDate;
    }

    public void setBuyDate(String buyDate) {
        this.buyDate = buyDate;
    }

    public int getAttributeType() {
        return attributeType;
    }

    public void setAttributeType(int attributeType) {
        this.attributeType = attributeType;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getContractCode() {
        return contractCode;
    }

    public void setContractCode(String contractCode) {
        this.contractCode = contractCode;
    }

    public String getSubMarketCode() {
        return subMarketCode;
    }

    public void setSubMarketCode(String subMarketCode) {
        this.subMarketCode = subMarketCode;
    }

    public String getContractName() {
        return contractName;
    }

    public void setContractName(String contractName) {
        this.contractName = contractName;
    }

    public String getRuleName() {
        return ruleName;
    }

    public void setRuleName(String ruleName) {
        this.ruleName = ruleName;
    }
}
