package com.cai.framework.store;

import android.content.Context;

/**
 * Created by clarence on 2018/1/23.
 */

public class StoreFactory {

    Context context;
    boolean initFinish = false;

    private static final class Holder {
        private static final StoreFactory instance = new StoreFactory();
    }

    /**
     * 必须先初始化
     *
     * @param context
     */
    public static void init(Context context) {
        Holder.instance.context = context;
        Holder.instance.initFinish = true;
    }

    public IStore getInstance(StoreType type) {
        if (!initFinish) {
            try {
                throw new Exception("请先调用StoreFactory.init()初始化！！！");
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        } else {
            return generateStore(type);
        }
    }

    private IStore generateStore(StoreType type) {
        IStore store = null;
        switch (type) {
            case SHARED_PREFERENCE:
                store = new StoreSharedPreferences();
                break;
            case File:
                store = new StoreFile();
                break;
            case HTTP:
                store = new StoreHttp();
                break;
            case SDCARD:
                store = new StoreSdCard();
                break;
            case SQLITE:
                store = new StoreSQLite();
                break;
            case CONTENT_PROVIDER:
                store = new StoreProvider();
                break;
        }
        return store;
    }
}
