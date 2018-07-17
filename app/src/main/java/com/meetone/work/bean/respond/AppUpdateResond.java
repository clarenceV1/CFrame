package com.meetone.work.bean.respond;

import com.meetone.work.bean.AppUpdate;

public class AppUpdateResond extends BaseRespond{
    private AppUpdate data;

    public AppUpdate getData() {
        return data;
    }

    public void setData(AppUpdate data) {
        this.data = data;
    }
}
