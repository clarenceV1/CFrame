package com.cai.work.bean;

public class Asset {

    private String token_name;
    private String token_id;
    private String candy_num;
    private String token_icon;
    private String price;
    private String assets;
    private int type;

    public String getToken_name() {
        return token_name;
    }

    public void setToken_name(String token_name) {
        this.token_name = token_name;
    }

    public String getToken_id() {
        return token_id;
    }

    public void setToken_id(String token_id) {
        this.token_id = token_id;
    }

    public String getCandy_num() {
        return candy_num;
    }

    public void setCandy_num(String candy_num) {
        this.candy_num = candy_num;
    }

    public String getToken_icon() {
        return token_icon;
    }

    public void setToken_icon(String token_icon) {
        this.token_icon = token_icon;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getAssets() {
        return assets;
    }

    public void setAssets(String assets) {
        this.assets = assets;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
