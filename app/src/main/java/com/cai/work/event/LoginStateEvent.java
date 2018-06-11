package com.cai.work.event;

public class LoginStateEvent {
    public int loginState;//1:登录，2退出

    public LoginStateEvent(int loginState) {
        this.loginState = loginState;
    }
}
