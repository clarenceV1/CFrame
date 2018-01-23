package com.cai.work.bean;

/**
 * Created by clarence on 2018/1/16.
 */

public class Weatherinfo {
    /**
     * 温度
     */
    private String temperature;
    /**
     * 湿度
     */
    private String Humidity;
    /**
     * 雾
     */
    private String info;
    /**
     * img
     */
    private String img;

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getHumidity() {
        return Humidity;
    }

    public void setHumidity(String humidity) {
        Humidity = humidity;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
