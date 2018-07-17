package com.meetone.work.bean.respond;


import com.meetone.work.bean.User;

public class LoginRespond extends BaseRespond {
    private User data;

    public User getData() {
        return data;
    }

    public void setData(User data) {
        this.data = data;
    }
}
