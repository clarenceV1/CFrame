package com.cai.work.bean.home;

import java.util.List;

public class HomeItemData {
    private HomeStockData stock;//data.stock	股票信息
    private List<HomeNphyData> nphy;//data.nphy	期货--内盘
    private List<HomeWphyData> wphy;//data.wphy	期货--外盘
    private List<HomeNoticeData> notice; //data.notice	公告---信息
    private List<HomeRangeData> range;//data.range	排行旁

    public HomeStockData getStock() {
        return stock;
    }

    public void setStock(HomeStockData stock) {
        this.stock = stock;
    }

    public List<HomeNphyData> getNphy() {
        return nphy;
    }

    public void setNphy(List<HomeNphyData> nphy) {
        this.nphy = nphy;
    }

    public List<HomeWphyData> getWphy() {
        return wphy;
    }

    public void setWphy(List<HomeWphyData> wphy) {
        this.wphy = wphy;
    }

    public List<HomeNoticeData> getNotice() {
        return notice;
    }

    public void setNotice(List<HomeNoticeData> notice) {
        this.notice = notice;
    }

    public List<HomeRangeData> getRange() {
        return range;
    }

    public void setRange(List<HomeRangeData> range) {
        this.range = range;
    }
}
