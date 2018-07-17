package com.meetone.work.bean;

import java.util.List;

public class Discover {

//    		"dis_id": 2,
//                    "dis_title": "敬请期待",
//                    "dis_bgimage": "https:\/\/static.ethte.com\/\/MoreOne\/image\/activity\/cup\/2018_07_04\/9173\/export.jpg",
//                    "dis_url": "https:\/\/static.ethte.com\/\/MoreOne\/image\/activity\/cup\/2018_07_04\/9173\/export.jpgen",
//                    "dis_uri": "https:\/\/static.ethte.com\/\/MoreOne\/image\/activity\/cup\/2018_07_04\/9173\/export.jpg"

    private int dis_id;
    private String dis_title;
    private String dis_bgimage;
    private String dis_url;
    private String dis_uri;
    private List<DiscoverMin> min;
    private boolean isHead;// 自己用，是否是头部

    public boolean isHead() {
        return isHead;
    }

    public void setHead(boolean head) {
        isHead = head;
    }

    public List<DiscoverMin> getMin() {
        return min;
    }

    public void setMin(List<DiscoverMin> min) {
        this.min = min;
    }

    public int getDis_id() {
        return dis_id;
    }

    public void setDis_id(int dis_id) {
        this.dis_id = dis_id;
    }

    public String getDis_title() {
        return dis_title;
    }

    public void setDis_title(String dis_title) {
        this.dis_title = dis_title;
    }

    public String getDis_bgimage() {
        return dis_bgimage;
    }

    public void setDis_bgimage(String dis_bgimage) {
        this.dis_bgimage = dis_bgimage;
    }

    public String getDis_url() {
        return dis_url;
    }

    public void setDis_url(String dis_url) {
        this.dis_url = dis_url;
    }

    public String getDis_uri() {
        return dis_uri;
    }

    public void setDis_uri(String dis_uri) {
        this.dis_uri = dis_uri;
    }
}