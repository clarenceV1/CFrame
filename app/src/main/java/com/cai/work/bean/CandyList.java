package com.cai.work.bean;

import com.cai.framework.bean.CBaseData;

/**
 * Created by davy on 2018/3/4.
 */

public class CandyList implements CBaseData {
    /**
     * [
     * {
     * "title": "11111",//广告标题
     * "uri: "",//跳转链接
     * "image": "http://img.weet.co/bg.jpg",//图片或背景图片
     * "type": 2 // type 1 糖果信息 type 2 广告信息
     * },
     * {
     * "candy_id": 1, //自增ID
     * "token_id": 1, //token(糖果)ID
     * "candy_total": 500000,//糖果总量
     * "wallet_total": 0,//当前持有糖果数量
     * "give_total": 100,//当前未领取数量（可以领取）
     * "candy_surplus": 473989.2952,//糖果剩余总数量
     * "token_name": "Candy Pie", //糖果名称
     * "token_symbol": "PIE",//糖果短名称
     * "token_zname": "糖果派",//糖果中文名
     * "token_icon": "http://img.weet.co/weet_logo.png",//logo
     * "token_remark": "金融生活服务链",//简介描述
     * "type": 1//type 1 糖果信息 type 2 广告信息
     * "wallet_total":100//当前持有数量
     * }
     * ]
     */
    private String title;
    private String uri;
    private String image;
    //
    private int candy_id;
    private int token_id;
    private float candy_total;
    private float give_total;
    private float candy_surplus;
    private String token_name;
    private String token_symbol;
    private String token_zname;
    private String token_icon;
    private String token_remark;
    private int type;
    private float wallet_total;
    private String barOne;
    private String barTwo;
    private int is_barTwo;//1显示，0不显示

    public String getBarOne() {
        return barOne;
    }

    public void setBarOne(String barOne) {
        this.barOne = barOne;
    }

    public String getBarTwo() {
        return barTwo;
    }

    public void setBarTwo(String barTwo) {
        this.barTwo = barTwo;
    }

    public int getIs_barTwo() {
        return is_barTwo;
    }

    public void setIs_barTwo(int is_barTwo) {
        this.is_barTwo = is_barTwo;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getCandy_id() {
        return candy_id;
    }

    public void setCandy_id(int candy_id) {
        this.candy_id = candy_id;
    }

    public int getToken_id() {
        return token_id;
    }

    public void setToken_id(int token_id) {
        this.token_id = token_id;
    }

    public float getCandy_total() {
        return candy_total;
    }

    public void setCandy_total(float candy_total) {
        this.candy_total = candy_total;
    }

    public float getGive_total() {
        return give_total;
    }

    public void setGive_total(float give_total) {
        this.give_total = give_total;
    }

    public float getCandy_surplus() {
        return candy_surplus;
    }

    public void setCandy_surplus(float candy_surplus) {
        this.candy_surplus = candy_surplus;
    }

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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public float getWallet_total() {
        return wallet_total;
    }

    public void setWallet_total(float wallet_total) {
        this.wallet_total = wallet_total;
    }
}
