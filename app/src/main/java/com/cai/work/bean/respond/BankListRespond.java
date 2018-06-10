package com.cai.work.bean.respond;

import com.cai.work.bean.Bank;

import java.util.List;

public class BankListRespond {
    private int code;
    private String responseText;
    private List<Bank> data;

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

    public List<Bank> getData() {
        return data;
    }

    public void setData(List<Bank> data) {
        this.data = data;
    }
}
