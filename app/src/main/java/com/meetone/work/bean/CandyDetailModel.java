package com.meetone.work.bean;

/**
 * Created by davy on 2018/3/10.
 */

public class CandyDetailModel extends BaseModel {
    /**
     * "data": {
     * "pro": {
     * "id": 1,
     * "token_name": "Candy Pie",//名称
     * "token_symbol": "PIE",//短名称
     * "token_zname": "糖果派",//中文名
     * "token_icon": "http://img.weet.co/weet_logo.png",
     * "token_remark": "金融生活服务链",//简介
     * "token_html": "https//weet.co/weet.html",//token详情(h5)
     * },
     * "user": {
     * "candy_total": 100//当前拥有糖果且为可提至钱包总量
     * }
     * }
     */
    private String token_name;
    private String token_symbol;
    private String token_zname;
    private String token_icon;
    private String token_remark;
    private String token_html;
    private String candy_total;

    public String getToken_name() {
        return token_name;
    }

    public void setToken_name(String token_name) {
        this.token_name = token_name;
    }

    public String getToken_symbol() {
        return token_symbol;
    }

    public void setToken_symbol(String token_symbol) {
        this.token_symbol = token_symbol;
    }

    public String getToken_zname() {
        return token_zname;
    }

    public void setToken_zname(String token_zname) {
        this.token_zname = token_zname;
    }

    public String getToken_icon() {
        return token_icon;
    }

    public void setToken_icon(String token_icon) {
        this.token_icon = token_icon;
    }

    public String getToken_remark() {
        return token_remark;
    }

    public void setToken_remark(String token_remark) {
        this.token_remark = token_remark;
    }

    public String getToken_html() {
        return token_html;
    }

    public void setToken_html(String token_html) {
        this.token_html = token_html;
    }

    public String getCandy_total() {
        return candy_total;
    }

    public void setCandy_total(String candy_total) {
        this.candy_total = candy_total;
    }
}
