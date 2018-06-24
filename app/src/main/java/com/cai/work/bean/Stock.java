package com.cai.work.bean;

import com.cai.framework.bean.CBaseData;

public class Stock implements CBaseData{

//    		"stockCode": "603917",
//                    "stockName": "\u5408\u529b\u79d1\u6280",
//                    "shortHand": "HLKJ",
//                    "stockMarket": "2"

    private String stockCode;
    private String stockName;
    private String shortHand;
    private String stockMarket;

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

    public String getShortHand() {
        return shortHand;
    }

    public void setShortHand(String shortHand) {
        this.shortHand = shortHand;
    }

    public String getStockMarket() {
        return stockMarket;
    }

    public void setStockMarket(String stockMarket) {
        this.stockMarket = stockMarket;
    }
}
