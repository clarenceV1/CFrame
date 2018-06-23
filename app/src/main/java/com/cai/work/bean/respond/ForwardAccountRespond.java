package com.cai.work.bean.respond;

import com.cai.work.bean.ForwardAccount;

import java.util.List;

public class ForwardAccountRespond extends BaseRespond {
    private List<ForwardAccount> data;

    public List<ForwardAccount> getData() {
        return data;
    }

    public void setData(List<ForwardAccount> data) {
        this.data = data;
    }
}
