package com.cai.work.bean;

/**
 * Created by clarence on 2018/2/5.
 */

public class Weather {
    private String date;
    private String message;
    private int status;
    private String city;
    private int count;
    private Weatherinfo data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Weatherinfo getData() {
        return data;
    }

    public void setData(Weatherinfo data) {
        this.data = data;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Weather{" +
                "date='" + date + '\'' +
                ", message='" + message + '\'' +
                ", status=" + status +
                ", city='" + city + '\'' +
                ", count=" + count +
                ", data='" + data + '\'' +
                '}';
    }
}
