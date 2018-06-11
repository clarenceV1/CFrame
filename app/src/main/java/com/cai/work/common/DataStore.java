package com.cai.work.common;

import com.example.clarence.datastorelibrary.store.share_preference.ISharePreference;

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
//
//    public void setToken(String value) {
//        sharePreference.get().write("LOGIN_TOKEN", value);
//    }
//
//    public String getToken() {
//        return sharePreference.get().read("LOGIN_TOKEN", "");
//    }
}
