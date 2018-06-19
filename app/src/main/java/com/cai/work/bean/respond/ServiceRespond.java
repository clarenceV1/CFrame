package com.cai.work.bean.respond;


import com.cai.work.bean.Service;

import java.util.List;

public class ServiceRespond extends BaseRespond{
    private List<Service> data;

    public List<Service> getData() {
        return data;
    }

    public void setData(List<Service> data) {
        this.data = data;
    }
}
