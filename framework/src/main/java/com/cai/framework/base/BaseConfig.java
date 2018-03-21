package com.cai.framework.base;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by clarence on 2018/3/21.
 */

public class BaseConfig {
    private Map<String, Object> switchMap = new HashMap<>();

    public static final String IS_DEBUG = "debug";

    private static class SingletonHolder {
        private static final BaseConfig instance = new BaseConfig();
    }

    private BaseConfig() {

    }

    public static final BaseConfig getInsatance() {
        return SingletonHolder.instance;
    }

    public void setSwitchMap(String key, Object value) {
        this.switchMap.put(key, value);
    }

    public boolean isDebug() {
        if (switchMap.get(IS_DEBUG) != null) {
            return (boolean) switchMap.get(IS_DEBUG);
        }
        return false;
    }
}
