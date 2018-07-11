package com.cai.work.bean;

import com.cai.framework.bean.CBaseData;

public class DiscoverMin implements CBaseData {

    private int dis_id;
    private String dis_title;
    private String dis_bgimage;
    private String dis_url;
    private String dis_uri;

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
