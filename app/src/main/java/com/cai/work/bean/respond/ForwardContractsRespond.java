package com.cai.work.bean.respond;

import java.util.Map;

public class ForwardContractsRespond extends BaseRespond {
    private Map<String,String> data;

    public Map<String, String> getData() {
        return data;
    }

    public void setData(Map<String, String> data) {
        this.data = data;
    }
}
