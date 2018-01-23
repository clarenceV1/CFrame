package com.cai.work.bean;

import java.util.ArrayList;

/**
 * Created by clarence on 2018/1/16.
 */

public class Weather {
    /**
     * 返回码
     */
    private int code;

    /**
     * 返回说明
     */
    private String reason;
    /**
     * 当前城市
     */
    private String city_name;
    /**
     * 当前时间
     */
    private String date;
    private String week;
    private String moon;
    /**
     * 更新时间
     */
    private String update_time;

    /**
     * 当前实际天气
     */
    private Weatherinfo weather;
    /**
     * 风
     */
    private Wind wind;
    /**
     * 生活指数
     */
    private Life life;
    /**
     * 未来几天的天气状况
     */
    private ArrayList<Forweather> forweathers;
    /**
     * pm值
     */
    private Pm pm;

    public Pm getPm() {
        return pm;
    }

    public void setPm(Pm pm) {
        this.pm = pm;
    }

    public Wind getWind() {
        return wind;
    }

    public void setWind(Wind wind) {
        this.wind = wind;
    }

    public Life getLife() {
        return life;
    }

    public void setLife(Life life) {
        this.life = life;
    }

    public ArrayList<Forweather> getForweathers() {
        return forweathers;
    }

    public void setForweathers(ArrayList<Forweather> forweathers) {
        this.forweathers = forweathers;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getCity_name() {
        return city_name;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public String getMoon() {
        return moon;
    }

    public void setMoon(String moon) {
        this.moon = moon;
    }

    public String getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }

    public Weatherinfo getWeather() {
        return weather;
    }

    public void setWeather(Weatherinfo weather) {
        this.weather = weather;
    }

    @Override
    public String toString() {
        return "Weather{" +
                "code=" + code +
                ", reason='" + reason + '\'' +
                ", city_name='" + city_name + '\'' +
                ", date='" + date + '\'' +
                ", week='" + week + '\'' +
                ", moon='" + moon + '\'' +
                ", update_time='" + update_time + '\'' +
                ", weather=" + weather +
                ", wind=" + wind +
                ", life=" + life +
                ", forweathers=" + forweathers +
                ", pm=" + pm +
                '}';
    }
}
