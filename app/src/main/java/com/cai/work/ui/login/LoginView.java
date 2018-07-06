package com.cai.work.ui.login;

import com.cai.work.bean.PhoneCode;

public interface LoginView {
    void callBack(PhoneCode data);

    void callBack(String message);
}
