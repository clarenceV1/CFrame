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

    public void saveAppUpdate(String json) {
        sharePreference.get().write("AppUpdate", json);
    }

    /**
     * 保存语言
     *
     * @param language
     */
    public void setLanguage(String language) {
        sharePreference.get().write("app_language", language);
    }

    public String getLanguage() {
        return sharePreference.get().read("app_language", "zh");
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
