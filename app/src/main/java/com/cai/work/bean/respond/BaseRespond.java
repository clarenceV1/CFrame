package com.cai.work.bean.respond;

public class BaseRespond {
    private int code;
    private String responseText;

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
}
