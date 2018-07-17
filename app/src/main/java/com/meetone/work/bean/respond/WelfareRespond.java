package com.meetone.work.bean.respond;

import com.meetone.work.bean.Welfare;

import java.util.List;

public class WelfareRespond extends BaseRespond {

    private List<Welfare> data;

    public List<Welfare> getData() {
        return data;
    }

    public void setData(List<Welfare> data) {
        this.data = data;
    }
}
