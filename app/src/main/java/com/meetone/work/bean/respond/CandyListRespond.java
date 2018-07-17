package com.meetone.work.bean.respond;


import com.meetone.work.bean.CandyList;

import java.util.List;

public class CandyListRespond extends BaseRespond {
    private List<CandyList> data;

    public List<CandyList> getData() {
        return data;
    }

    public void setData(List<CandyList> data) {
        this.data = data;
    }
}
