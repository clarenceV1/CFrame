package com.cai.work.bean;

public class ForwardDetail {
//    {
//        "mk_price": "1320.1",    // 最新价
//            "contractName": "GC1806",// 合约名称
//            "open_price": "1321.8",//合约代码
//            "pMLimit": "1322.3",//上次结算价
//            "yt_price": "1322.3",// 上次结算价
//            "sp1": "1320.2",//申卖价一
//            "sn1": "34",//申卖量一
//            "bp1": "1320.1",//申买价一
//            "bn1": "15",//申买量一
//            "up_price": "1322.4",//  最高价
//            "dn_price": "1319.3",// 最低价
//            "limit_up_price": "1412.3",//涨停板价
//            "limit_dn_price": "1232.3",//跌停板价
//            "kp_price": "1321.8",// 开盘价
//            "zhangdie": "-2.2",//  涨跌
//            "cjl": "1322.3",//今收盘
//            "update_time": "2018-05-11 12:39:55",  //更新时间
//            "zhangfu": "-0.166"  //涨跌幅
//    }

//    public static ForwardDetail getMockData() {
//        ForwardDetail detail = new ForwardDetail();
//        detail.setMk_price("1320.1");
//        detail.setContractName("GC1806");
//        detail.setOpen_price("1321.8");
//        detail.setpMLimit("1322.3");
//        detail.setYt_price("1322.3");
//        detail.setSn1("1320.2");
//        detail.setSn1("34");
//        detail.setBp1("1320.1");
//        detail.setBn1("15");
//        detail.setUp_price("1322.4");
//        detail.setDn_price("1319.3");
//        detail.setLimit_up_price("1412.3");
//        detail.setLimit_dn_price("1232.3");
//        detail.setKp_price("1321.8");
//        detail.setZhangdie("-2.2");
//        detail.setCjl("1322.3");
//        detail.setUpdate_time("2018-05-11 12:39:55");
//        detail.setZhangfu("-0.166");
//        return detail;
//    }

    private String mk_price;
    private String contractName;
    private String open_price;
    private String pMLimit;
    private String yt_price;
    private String sp1;
    private String sn1;
    private String bp1;
    private String bn1;
    private String up_price;
    private String dn_price;
    private String limit_up_price;
    private String limit_dn_price;
    private String kp_price;
    private String zhangdie;
    private String cjl;
    private String update_time;
    private String zhangfu;

    public String getMk_price() {
        return mk_price;
    }

    public void setMk_price(String mk_price) {
        this.mk_price = mk_price;
    }

    public String getContractName() {
        return contractName;
    }

    public void setContractName(String contractName) {
        this.contractName = contractName;
    }

    public String getOpen_price() {
        return open_price;
    }

    public void setOpen_price(String open_price) {
        this.open_price = open_price;
    }

    public String getpMLimit() {
        return pMLimit;
    }

    public void setpMLimit(String pMLimit) {
        this.pMLimit = pMLimit;
    }

    public String getYt_price() {
        return yt_price;
    }

    public void setYt_price(String yt_price) {
        this.yt_price = yt_price;
    }

    public String getSp1() {
        return sp1;
    }

    public void setSp1(String sp1) {
        this.sp1 = sp1;
    }

    public String getSn1() {
        return sn1;
    }

    public void setSn1(String sn1) {
        this.sn1 = sn1;
    }

    public String getBp1() {
        return bp1;
    }

    public void setBp1(String bp1) {
        this.bp1 = bp1;
    }

    public String getBn1() {
        return bn1;
    }

    public void setBn1(String bn1) {
        this.bn1 = bn1;
    }

    public String getUp_price() {
        return up_price;
    }

    public void setUp_price(String up_price) {
        this.up_price = up_price;
    }

    public String getDn_price() {
        return dn_price;
    }

    public void setDn_price(String dn_price) {
        this.dn_price = dn_price;
    }

    public String getLimit_up_price() {
        return limit_up_price;
    }

    public void setLimit_up_price(String limit_up_price) {
        this.limit_up_price = limit_up_price;
    }

    public String getLimit_dn_price() {
        return limit_dn_price;
    }

    public void setLimit_dn_price(String limit_dn_price) {
        this.limit_dn_price = limit_dn_price;
    }

    public String getKp_price() {
        return kp_price;
    }

    public void setKp_price(String kp_price) {
        this.kp_price = kp_price;
    }

    public String getZhangdie() {
        return zhangdie;
    }

    public void setZhangdie(String zhangdie) {
        this.zhangdie = zhangdie;
    }

    public String getCjl() {
        return cjl;
    }

    public void setCjl(String cjl) {
        this.cjl = cjl;
    }

    public String getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }

    public String getZhangfu() {
        return zhangfu;
    }

    public void setZhangfu(String zhangfu) {
        this.zhangfu = zhangfu;
    }
}
