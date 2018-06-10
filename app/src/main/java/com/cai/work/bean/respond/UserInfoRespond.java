package com.cai.work.bean.respond;

import com.cai.work.bean.Account;

public class UserInfoRespond extends BaseRespond {
    private Account data;

    public Account getData() {
        return data;
    }

    public void setData(Account data) {
        this.data = data;
    }
}
