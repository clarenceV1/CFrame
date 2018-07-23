package com.cai.work.bean;

import com.cai.framework.bean.CBaseData;

public class RebateItem implements CBaseData {
    /*    data.oneLever.id	返佣id	number
        data.oneLever.lever	级别（1：一级 2：二级）	number
        data.oneLever.isWithdraw	是否提现	number
        data.oneLever.feeCharge	返佣利息费	number
        data.oneLever.interestMoney	金额	number
        data.oneLever.order_date	订单日期	string
        data.oneLever.realName	邀请人	string
        data.oneLever.inviteName	交易人*/
    private int id;
    private int lever;
    private int isWithdraw;
    private String feeCharge;
    private String interestMoney;
    private String order_date;
    private String realName;
    private String invitename;
    private String product;//产品
    private boolean isChoosed;//是否被选中

    public boolean isChoosed() {
        return isChoosed;
    }

    public void setChoosed(boolean choosed) {
        isChoosed = choosed;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLever() {
        return lever;
    }

    public void setLever(int lever) {
        this.lever = lever;
    }

    public int getIsWithdraw() {
        return isWithdraw;
    }

    public void setIsWithdraw(int isWithdraw) {
        this.isWithdraw = isWithdraw;
    }

    public String getFeeCharge() {
        return feeCharge;
    }

    public void setFeeCharge(String feeCharge) {
        this.feeCharge = feeCharge;
    }

    public String getInterestMoney() {
        return interestMoney;
    }

    public void setInterestMoney(String interestMoney) {
        this.interestMoney = interestMoney;
    }

    public String getOrder_date() {
        return order_date;
    }

    public void setOrder_date(String order_date) {
        this.order_date = order_date;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getInvitename() {
        return invitename;
    }

    public void setInvitename(String invitename) {
        this.invitename = invitename;
    }
}
