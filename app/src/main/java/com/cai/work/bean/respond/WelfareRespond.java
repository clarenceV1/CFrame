package com.cai.work.bean.respond;

import com.cai.work.bean.Welfare;

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
