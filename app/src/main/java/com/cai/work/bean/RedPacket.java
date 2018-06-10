package com.cai.work.bean;

import java.util.List;

public class RedPacket {
    private int total;
    private int total_page;
    private int current;
    private List<RedPacketItem> data;

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

    public List<RedPacketItem> getData() {
        return data;
    }

    public void setData(List<RedPacketItem> data) {
        this.data = data;
    }
}
