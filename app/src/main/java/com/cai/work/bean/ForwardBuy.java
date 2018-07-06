package com.cai.work.bean;

import java.util.List;

public class ForwardBuy {
    private String code;
    private String name;
    private String price;
    private String type;
    private String attributeType;
    private int[] amount;
    private float[] bond;
    private float[] zs;
    private float[] zy;
    private float cost;
    private String buyDate;
    private String isMonth;
    private String nightTime;
    private String[] holdTime;
    private String balance;
    private String virtualBalance;
    private String socket_host;
    private String socket_port;
    private List<StockBuyRedBag> redBags;

    public List<StockBuyRedBag> getRedBags() {
        return redBags;
    }

    public void setRedBags(List<StockBuyRedBag> redBags) {
        this.redBags = redBags;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int[] getAmount() {
        return amount;
    }

    public void setAmount(int[] amount) {
        this.amount = amount;
    }

    public float[] getBond() {
        return bond;
    }

    public void setBond(float[] bond) {
        this.bond = bond;
    }

    public float[] getZs() {
        return zs;
    }

    public void setZs(float[] zs) {
        this.zs = zs;
    }

    public float[] getZy() {
        return zy;
    }

    public void setZy(float[] zy) {
        this.zy = zy;
    }

    public float getCost() {
        return cost;
    }

    public void setCost(float cost) {
        this.cost = cost;
    }

    public String getBuyDate() {
        return buyDate;
    }

    public void setBuyDate(String buyDate) {
        this.buyDate = buyDate;
    }

    public String getIsMonth() {
        return isMonth;
    }

    public void setIsMonth(String isMonth) {
        this.isMonth = isMonth;
    }

    public String getNightTime() {
        return nightTime;
    }

    public void setNightTime(String nightTime) {
        this.nightTime = nightTime;
    }

    public String getAttributeType() {
        return attributeType;
    }

    public void setAttributeType(String attributeType) {
        this.attributeType = attributeType;
    }

    public String getVirtualBalance() {
        return virtualBalance;
    }

    public void setVirtualBalance(String virtualBalance) {
        this.virtualBalance = virtualBalance;
    }

    public String getSocket_host() {
        return socket_host;
    }

    public void setSocket_host(String socket_host) {
        this.socket_host = socket_host;
    }

    public String getSocket_port() {
        return socket_port;
    }

    public void setSocket_port(String socket_port) {
        this.socket_port = socket_port;
    }

    public String[] getHoldTime() {
        return holdTime;
    }

    public void setHoldTime(String[] holdTime) {
        this.holdTime = holdTime;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }
}
