package com.cai.work.common;


import com.cai.framework.share_preference.ISharePreference;

import javax.inject.Inject;

import dagger.Lazy;

/**
 * Created by clarence on 2018/3/26.
 */

public class DataStore {
    @Inject
    Lazy<ISharePreference> sharePreference;

    @Inject
    public DataStore() {
    }

    public void saveAccount(String value) {
        sharePreference.get().write("LOGIN_ACCOUNT", value);
    }

    public String getAccount() {
        return sharePreference.get().read("LOGIN_ACCOUNT", "");
    }

    public void savePw(String value) {
        sharePreference.get().write("LOGIN_PWD", value);
    }

    public String getPw() {
        return sharePreference.get().read("LOGIN_PWD", "");
    }
}
