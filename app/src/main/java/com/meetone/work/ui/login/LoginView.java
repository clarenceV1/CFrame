package com.meetone.work.ui.login;

import com.meetone.work.bean.PhoneCode;
import com.meetone.work.bean.User;

public interface LoginView {
    void callBack(PhoneCode data);

    void callBack(String message);

    void callBack(User user);
}
