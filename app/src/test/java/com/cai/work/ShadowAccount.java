package com.cai.work;


import com.cai.work.bean.Account;

import org.robolectric.annotation.Implementation;
import org.robolectric.annotation.Implements;

@Implements(Account.class)
public class ShadowAccount {

    @Implementation
    public String getName() {
        return "xiaocai";
    }

    @Implementation
    public String getPassword() {
        return "123";
    }
}
