package com.cai.work.bean;

public class StockHQ {
    private float bp1;
    private float bp2;
    private float bp3;
    private float bp4;
    private float bp5;

    private int bn1;
    private int bn2;
    private int bn3;
    private int bn4;
    private int bn5;

    private float sp1;
    private float sp2;
    private float sp3;
    private float sp4;
    private float sp5;

    private int sn1;
    private int sn2;
    private int sn3;
    private int sn4;
    private int sn5;

    private String up_limit;
    private String dn_limit;
    private String md_limit;
    private String up_price;
    private String dn_price;
    private String mk_code;
    private String stock_name;
    private String stock_code;

    private String tingpai;
    private String mk_price;
    private float kp_price;
    private String zhangdie;
    private String zhangfu;
    private String huanshou;
    private String cjje;
    private String cjss;

    private String neipan;
    private String waipan;

    private String zhenfu;
    private String stCode;

    private String stName;
    private String pULimit;
    private String pDLimit;
    private String mkCode;

//    data.bp1	买1价格	number
//    data.bp2	买2价格	number
//    data.bp3	买3价格	number
//    data.bp4	买4价格	number
//    data.bp5	买5价格	number
//    data.bn1	买1数量	number
//    data.bn2	买2数量	number
//    data.bn3	买3数量	number
//    data.bn4	买4数量	number
//    data.bn5	买5数量	number
//    data.sp1	卖1价格	number
//    data.sp2	卖2价格	number
//    data.sp3	卖3价格	number
//    data.sp4	卖4价格	number
//    data.sp5	卖5价格	number
//    data.sn1	卖1数量	number
//    data.sn2	卖2数量	number
//    data.sn3	卖3数量	number
//    data.sn4	卖4数量	number
//    data.sn5	卖5数量	number
//    data.up_limit	涨停价	number
//    data.dn_limit	跌停价	number
//    data.md_limit	昨日收盘价	number
//    data.up_price	最高价	number
//    data.dn_price	最低价	number
//    data.mk_code	市场类型 备注1为深圳(SZ)，2上海(SH)	number
//    data.stock_name	股票名称	string
//    data.stock_code	股票代码	number
//    data.tingpai	停复牌 例:0正常1停牌	number
//    data.mk_price	最新价	number
//    data.kp_price	开盘价	number
//    data.zhangdie	涨跌点数	number
//    data.zhangfu	换手率(百分比)	number
//    data.huanshou	换手率(百分比)	number
//    data.cjje	成交金额	number
//    data.cjss	成交数量	number
//    data.neipan	内盘买数量	number
//    data.waipan	外盘买数量	number
//    data.zhenfu	振幅(百分比) 例：6.07 则今天振幅为6.07%	number
//    data.stCode	股票代码	number
//    data.stName	股票名称	string
//    data.pULimit	涨停价	number
//    data.pDLimit	跌停价	number
//    data.mkCode	市场类型 备注1为深圳(SZ)，2上海(SH)


    public float getBp1() {
        return bp1;
    }

    public void setBp1(float bp1) {
        this.bp1 = bp1;
    }

    public float getBp2() {
        return bp2;
    }

    public void setBp2(float bp2) {
        this.bp2 = bp2;
    }

    public float getBp3() {
        return bp3;
    }

    public int getSn1() {
        return sn1;
    }

    public int getSn2() {
        return sn2;
    }

    public int getSn3() {
        return sn3;
    }

    public int getSn4() {
        return sn4;
    }

    public int getSn5() {
        return sn5;
    }

    public void setBp3(float bp3) {
        this.bp3 = bp3;
    }

    public float getBp4() {
        return bp4;
    }

    public void setBp4(float bp4) {
        this.bp4 = bp4;
    }

    public float getBp5() {
        return bp5;
    }

    public void setBp5(float bp5) {
        this.bp5 = bp5;
    }

    public int getBn1() {
        return bn1;
    }

    public void setBn1(int bn1) {
        this.bn1 = bn1;
    }

    public int getBn2() {
        return bn2;
    }

    public void setBn2(int bn2) {
        this.bn2 = bn2;
    }

    public int getBn3() {
        return bn3;
    }

    public void setBn3(int bn3) {
        this.bn3 = bn3;
    }

    public int getBn4() {
        return bn4;
    }

    public void setBn4(int bn4) {
        this.bn4 = bn4;
    }

    public int getBn5() {
        return bn5;
    }

    public void setBn5(int bn5) {
        this.bn5 = bn5;
    }

    public void setSn1(int sn1) {
        this.sn1 = sn1;
    }

    public void setSn2(int sn2) {
        this.sn2 = sn2;
    }

    public void setSn3(int sn3) {
        this.sn3 = sn3;
    }

    public void setSn4(int sn4) {
        this.sn4 = sn4;
    }

    public void setSn5(int sn5) {
        this.sn5 = sn5;
    }

    public float getSp1() {
        return sp1;
    }

    public void setSp1(float sp1) {
        this.sp1 = sp1;
    }

    public float getSp2() {
        return sp2;
    }

    public void setSp2(float sp2) {
        this.sp2 = sp2;
    }

    public float getSp3() {
        return sp3;
    }

    public void setSp3(float sp3) {
        this.sp3 = sp3;
    }

    public float getSp4() {
        return sp4;
    }

    public void setSp4(float sp4) {
        this.sp4 = sp4;
    }

    public float getSp5() {
        return sp5;
    }

    public void setSp5(float sp5) {
        this.sp5 = sp5;
    }

    public String getUp_limit() {
        return up_limit;
    }

    public void setUp_limit(String up_limit) {
        this.up_limit = up_limit;
    }

    public String getDn_limit() {
        return dn_limit;
    }

    public void setDn_limit(String dn_limit) {
        this.dn_limit = dn_limit;
    }

    public String getMd_limit() {
        return md_limit;
    }

    public void setMd_limit(String md_limit) {
        this.md_limit = md_limit;
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

    public String getMk_code() {
        return mk_code;
    }

    public void setMk_code(String mk_code) {
        this.mk_code = mk_code;
    }

    public String getStock_name() {
        return stock_name;
    }

    public void setStock_name(String stock_name) {
        this.stock_name = stock_name;
    }

    public String getStock_code() {
        return stock_code;
    }

    public void setStock_code(String stock_code) {
        this.stock_code = stock_code;
    }

    public String getTingpai() {
        return tingpai;
    }

    public void setTingpai(String tingpai) {
        this.tingpai = tingpai;
    }

    public String getMk_price() {
        return mk_price;
    }

    public void setMk_price(String mk_price) {
        this.mk_price = mk_price;
    }

    public float getKp_price() {
        return kp_price;
    }

    public void setKp_price(float kp_price) {
        this.kp_price = kp_price;
    }

    public String getZhangdie() {
        return zhangdie;
    }

    public void setZhangdie(String zhangdie) {
        this.zhangdie = zhangdie;
    }

    public String getZhangfu() {
        return zhangfu;
    }

    public void setZhangfu(String zhangfu) {
        this.zhangfu = zhangfu;
    }

    public String getHuanshou() {
        return huanshou;
    }

    public void setHuanshou(String huanshou) {
        this.huanshou = huanshou;
    }

    public String getCjje() {
        return cjje;
    }

    public void setCjje(String cjje) {
        this.cjje = cjje;
    }

    public String getCjss() {
        return cjss;
    }

    public void setCjss(String cjss) {
        this.cjss = cjss;
    }

    public String getNeipan() {
        return neipan;
    }

    public void setNeipan(String neipan) {
        this.neipan = neipan;
    }

    public String getWaipan() {
        return waipan;
    }

    public void setWaipan(String waipan) {
        this.waipan = waipan;
    }

    public String getZhenfu() {
        return zhenfu;
    }

    public void setZhenfu(String zhenfu) {
        this.zhenfu = zhenfu;
    }

    public String getStCode() {
        return stCode;
    }

    public void setStCode(String stCode) {
        this.stCode = stCode;
    }

    public String getStName() {
        return stName;
    }

    public void setStName(String stName) {
        this.stName = stName;
    }

    public String getpULimit() {
        return pULimit;
    }

    public void setpULimit(String pULimit) {
        this.pULimit = pULimit;
    }

    public String getpDLimit() {
        return pDLimit;
    }

    public void setpDLimit(String pDLimit) {
        this.pDLimit = pDLimit;
    }

    public String getMkCode() {
        return mkCode;
    }

    public void setMkCode(String mkCode) {
        this.mkCode = mkCode;
    }
}
