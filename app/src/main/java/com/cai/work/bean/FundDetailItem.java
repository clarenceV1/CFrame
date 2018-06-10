package com.cai.work.bean;

import com.cai.framework.bean.CBaseData;

public class FundDetailItem implements CBaseData {
//    data.total	总数量	number
//    data.total_page	总页数	number
//    data.current	当前页数1	number
//    data.data		array
//    data.data.relatedType	交易类型	string
//    data.data.amount	发生金额	number
//    data.data.createDate	创建时间	string
//    data.data.inoutType	收支标志 1:收入 -1:支出	number
//    data.data.remark	备注说明
    private String relatedType;
    private String amount;
    private String createDate;
    private int inoutType;
    private String remark;

    public String getRelatedType() {
        return relatedType;
    }

    public void setRelatedType(String relatedType) {
        this.relatedType = relatedType;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public int getInoutType() {
        return inoutType;
    }

    public void setInoutType(int inoutType) {
        this.inoutType = inoutType;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
