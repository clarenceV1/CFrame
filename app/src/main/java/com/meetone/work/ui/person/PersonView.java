package com.meetone.work.ui.person;

import com.meetone.work.bean.User;

public interface PersonView {
    void callBack(User user);

    void callBack(String s);

    void loginout();
}
