package com.cai.work.common;

import com.example.clarence.datastorelibrary.store.share_preference.ISharePreference;

import javax.inject.Inject;

/**
 * Created by clarence on 2018/3/26.
 */

public class DataStore {
    @Inject
    ISharePreference sharePreference;

    @Inject
    public DataStore() {

    }

    public void saveAppUpdate(String json) {
        sharePreference.write("AppUpdate", json);
    }

    /**
     * 保存语言
     *
     * @param language
     */
    public void setLanguage(String language) {
        sharePreference.write("app_language", language);
    }

    public String getLanguage() {
        return sharePreference.read("app_language", "zh");
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
