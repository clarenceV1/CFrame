package com.cai.work.bean.respond;

import com.cai.work.bean.FundDetail;

public class FundDetailRespond {
    private int code;
    private String responseText;
    private FundDetail data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getResponseText() {
        return responseText;
    }

    public void setResponseText(String responseText) {
        this.responseText = responseText;
    }

    public FundDetail getData() {
        return data;
    }

    public void setData(FundDetail data) {
        this.data = data;
    }
}
