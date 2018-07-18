package com.cai.work.bean;

public class StockTrade {
    private String stock_code;
    private String stock_name;
    private String isTrade;
    private String tradeTime;
    private String shortMarket;

    public String getShortMarket() {
        return shortMarket;
    }

    public void setShortMarket(String shortMarket) {
        this.shortMarket = shortMarket;
    }

    public String getStock_code() {
        return stock_code;
    }

    public void setStock_code(String stock_code) {
        this.stock_code = stock_code;
    }

    public String getStock_name() {
        return stock_name;
    }

    public void setStock_name(String stock_name) {
        this.stock_name = stock_name;
    }

    public String getIsTrade() {
        return isTrade;
    }

    public void setIsTrade(String isTrade) {
        this.isTrade = isTrade;
    }

    public String getTradeTime() {
        return tradeTime;
    }

    public void setTradeTime(String tradeTime) {
        this.tradeTime = tradeTime;
    }
}
