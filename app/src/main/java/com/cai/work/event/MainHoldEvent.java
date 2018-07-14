package com.cai.work.event;

public class MainHoldEvent {
    public boolean isRealTrade = false;//是否是实盘交易
    public boolean isStock = false;//是股票还是期货
    public boolean isHolder = false;//是否是持仓 还是结算

    public MainHoldEvent(boolean isRealTrade, boolean isStock, boolean isHolder) {
        this.isRealTrade = isRealTrade;
        this.isStock = isStock;
        this.isHolder = isHolder;
    }
}
