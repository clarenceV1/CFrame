package com.cai.work.bean.home;

public class HomeData {
    private int code;
    private String responseText;
    private HomeItemData data;

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

    public HomeItemData getData() {
        return data;
    }

    public void setData(HomeItemData data) {
        this.data = data;
    }
}
