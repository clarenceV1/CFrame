package com.cai.work.kline;

/**
 * chart data model
 */

public class HisData {

    private double close;
    private double high;
    private double low;
    private double open;
    private long vol;
    private long date;
    private long amountVol;
    private double avePrice;
    private double total;
    private double maSum;

    public HisData() {
    }

    public double getClose() {
        return close;
    }

    public void setClose(double close) {
        this.close = close;
    }

    public double getHigh() {
        return high;
    }

    public void setHigh(double high) {
        this.high = high;
    }

    public double getLow() {
        return low;
    }

    public void setLow(double low) {
        this.low = low;
    }

    public double getOpen() {
        return open;
    }

    public void setOpen(double open) {
        this.open = open;
    }

    public long getVol() {
        return vol;
    }

    public void setVol(long vol) {
        this.vol = vol;
    }


    public double getAvePrice() {
        return avePrice;
    }

    public void setAvePrice(double avePrice) {
        this.avePrice = avePrice;
    }


    public long getAmountVol() {
        return amountVol;
    }

    public void setAmountVol(long amountVol) {
        this.amountVol = amountVol;
    }


    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        HisData data = (HisData) o;

        return date == data.date;
    }

    @Override
    public int hashCode() {
        return (int) (date ^ (date >>> 32));
    }
}
