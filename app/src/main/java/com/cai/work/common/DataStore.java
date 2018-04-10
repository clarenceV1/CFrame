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

    public void setTitle(String key, String value) {
        sharePreference.write(key, value);
    }

    public String getTitle(String key, String defaultValue) {
        return sharePreference.read(key, defaultValue);
    }
}
