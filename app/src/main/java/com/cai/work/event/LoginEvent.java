package com.cai.work.event;

public class LoginEvent {
    public final static int STATE_LOGIN_IN = 1;
    public final static int STATE_LOGIN_OUT = 2;
    public int loginState; //1:login 2:loginout

    public LoginEvent(int loginState) {
        this.loginState = loginState;
    }
}
