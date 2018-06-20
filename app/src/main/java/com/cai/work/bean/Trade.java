package com.cai.work.bean;

import com.cai.framework.bean.CBaseData;

import java.io.Serializable;
import java.util.List;

public class Trade implements Serializable,CBaseData{
    private List<TradeItem>  gn_contract;
    private List<TradeItem>  gj_contract;
    private TradeItem  stock;
    private String gn;//显示用的
    private String gj;//显示用的
    private String gp;//显示用的

    public String getGn() {
        return gn;
    }

    public void setGn(String gn) {
        this.gn = gn;
    }

    public String getGj() {
        return gj;
    }

    public void setGj(String gj) {
        this.gj = gj;
    }

    public String getGp() {
        return gp;
    }

    public void setGp(String gp) {
        this.gp = gp;
    }

    public List<TradeItem> getGn_contract() {
        return gn_contract;
    }

    public void setGn_contract(List<TradeItem> gn_contract) {
        this.gn_contract = gn_contract;
    }

    public List<TradeItem> getGj_contract() {
        return gj_contract;
    }

    public void setGj_contract(List<TradeItem> gj_contract) {
        this.gj_contract = gj_contract;
    }

    public TradeItem getStock() {
        return stock;
    }

    public void setStock(TradeItem stock) {
        this.stock = stock;
    }
}
