package com.cai.work.ui.login;

import com.cai.work.bean.PhoneCode;
import com.cai.work.bean.User;

public interface LoginView {
    void callBack(PhoneCode data);

    void callBack(String message);

    void callBack(User user);
}
