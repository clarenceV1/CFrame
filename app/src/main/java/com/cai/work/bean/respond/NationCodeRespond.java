package com.cai.work.bean.respond;

import com.cai.work.bean.NationCodeModel;

import java.util.List;

public class NationCodeRespond extends BaseRespond {
    private List<NationCodeModel> data;


    public List<NationCodeModel> getData() {
        return data;
    }

    public void setData(List<NationCodeModel> data) {
        this.data = data;
    }
}
