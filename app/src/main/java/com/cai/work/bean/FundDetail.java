package com.cai.work.bean;

public class FundDetail {
    private int total;
    private int total_page;
    private int current;
    private FundDetailItem data;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getTotal_page() {
        return total_page;
    }

    public void setTotal_page(int total_page) {
        this.total_page = total_page;
    }

    public int getCurrent() {
        return current;
    }

    public void setCurrent(int current) {
        this.current = current;
    }

    public FundDetailItem getData() {
        return data;
    }

    public void setData(FundDetailItem data) {
        this.data = data;
    }
}
