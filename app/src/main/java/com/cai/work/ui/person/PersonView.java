package com.cai.work.ui.person;

import com.cai.work.bean.User;

public interface PersonView {
    void callBack(User user);

    void callBack(String s);

    void loginout();
}
