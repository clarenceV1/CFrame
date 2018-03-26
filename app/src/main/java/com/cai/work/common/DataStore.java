package com.cai.work.common;

import com.example.clarence.datastorelibrary.store.StoreFactory;
import com.example.clarence.datastorelibrary.store.base.StoreType;

import javax.inject.Inject;

/**
 * Created by clarence on 2018/3/26.
 */

public class DataStore {
    @Inject
    public DataStore() {
    }

    public void setTitle(String key, String value) {
        StoreFactory.getInstance(StoreType.SHARED_PREFERENCE).write(key, value);
    }

    public String getTitle(String key, String defaultValue) {
        return StoreFactory.getInstance(StoreType.SHARED_PREFERENCE).read(key, defaultValue);
    }
}
