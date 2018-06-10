package com.cai.work.bean;

import com.cai.framework.bean.CBaseData;

public class RedPacketItem implements CBaseData{
//    data.data.parValue	红包值	number
//    data.data.dueDate	过期时间	string
//    data.data.redText	红包类型	string
//    data.data.status	状态1未使用2已使用
    private String parValue;
    private String dueDate;
    private String redText;
    private int status;

    public String getParValue() {
        return parValue;
    }

    public void setParValue(String parValue) {
        this.parValue = parValue;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getRedText() {
        return redText;
    }

    public void setRedText(String redText) {
        this.redText = redText;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
