package com.cai.work.bean.home;

import com.cai.framework.bean.CBaseData;

import java.io.Serializable;

public class HomeRangeData implements CBaseData,Serializable {
//    data.range	排行旁	array
//    data.range.principal	操盘资金	string
//    data.range.buyWTDate	操盘时间	string
//    data.range.user	操盘会员
    private String principal;
    private String buyWTDate;
    private String user;

    public String getPrincipal() {
        return principal;
    }

    public void setPrincipal(String principal) {
        this.principal = principal;
    }

    public String getBuyWTDate() {
        return buyWTDate;
    }

    public void setBuyWTDate(String buyWTDate) {
        this.buyWTDate = buyWTDate;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

}
