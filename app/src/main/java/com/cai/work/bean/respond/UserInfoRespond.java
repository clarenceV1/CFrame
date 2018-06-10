package com.cai.work.bean.respond;

import com.cai.work.bean.User;

public class UserInfoRespond extends BaseRespond {
    private User data;

    public User getData() {
        return data;
    }

    public void setData(User data) {
        this.data = data;
    }
}
