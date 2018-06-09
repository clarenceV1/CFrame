package com.cai.work.bean.respond;

import com.cai.work.bean.Account;

public class UserInfoRespond {
    private int code;
    private String responseText;
    private Account data;

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

    public Account getData() {
        return data;
    }

    public void setData(Account data) {
        this.data = data;
    }
}
