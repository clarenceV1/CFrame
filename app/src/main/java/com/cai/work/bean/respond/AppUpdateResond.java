package com.cai.work.bean.respond;

import com.cai.work.bean.AppUpdate;
import com.cai.work.bean.BaseRespond;

public class AppUpdateResond extends BaseRespond{
    private AppUpdate data;

    public AppUpdate getData() {
        return data;
    }

    public void setData(AppUpdate data) {
        this.data = data;
    }
}
