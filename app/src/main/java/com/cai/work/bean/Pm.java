package com.cai.work.bean;

/**
 * Created by clarence on 2018/1/16.
 */

class Pm {
    private String key;
    private String show_desc;
    private String dateTime;
    private String cityName;
    private InnerPm ipm;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getShow_desc() {
        return show_desc;
    }

    public void setShow_desc(String show_desc) {
        this.show_desc = show_desc;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public InnerPm getIpm() {
        return ipm;
    }

    public void setIpm(InnerPm ipm) {
        this.ipm = ipm;
    }
}
