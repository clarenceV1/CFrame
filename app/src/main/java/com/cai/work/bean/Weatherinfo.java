package com.cai.work.bean;

import java.util.List;

/**
 * Created by clarence on 2018/1/16.
 */

public class Weatherinfo {
    private String shidu;
    private String pm25;
    private String pm10;
    private String quality;
    private String wendu;
    private String ganmao;
    private WeatherYesterday yesterday;
    private List<WeatherYesterday> forecast;

    public String getShidu() {
        return shidu;
    }

    public void setShidu(String shidu) {
        this.shidu = shidu;
    }

    public String getPm25() {
        return pm25;
    }

    public void setPm25(String pm25) {
        this.pm25 = pm25;
    }

    public String getPm10() {
        return pm10;
    }

    public void setPm10(String pm10) {
        this.pm10 = pm10;
    }

    public String getQuality() {
        return quality;
    }

    public void setQuality(String quality) {
        this.quality = quality;
    }

    public String getWendu() {
        return wendu;
    }

    public void setWendu(String wendu) {
        this.wendu = wendu;
    }

    public String getGanmao() {
        return ganmao;
    }

    public void setGanmao(String ganmao) {
        this.ganmao = ganmao;
    }

    public WeatherYesterday getYesterday() {
        return yesterday;
    }

    public void setYesterday(WeatherYesterday yesterday) {
        this.yesterday = yesterday;
    }

    public List<WeatherYesterday> getForecast() {
        return forecast;
    }

    public void setForecast(List<WeatherYesterday> forecast) {
        this.forecast = forecast;
    }

    @Override
    public String toString() {
        return "Weatherinfo{" +
                "shidu='" + shidu + '\'' +
                ", pm25='" + pm25 + '\'' +
                ", pm10='" + pm10 + '\'' +
                ", quality='" + quality + '\'' +
                ", wendu='" + wendu + '\'' +
                ", ganmao='" + ganmao + '\'' +
                ", yesterday=" + yesterday +
                ", forecast=" + forecast +
                '}';
    }
}
