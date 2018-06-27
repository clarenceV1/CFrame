package com.cai.work.common;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.cai.work.bean.MineModel;
import com.example.clarence.datastorelibrary.store.share_preference.ISharePreference;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by clarence on 2018/3/26.
 */
@Singleton
public class DataCacheStore {
    @Inject
    ISharePreference sharePreference;

    private static final String MINE_DATA = "mine_data";

    @Inject
    public DataCacheStore() {

    }

    public void saveMineData(MineModel mineModel) {
        String json = JSON.toJSONString(mineModel);
        sharePreference.write(MINE_DATA, json);
    }

    public MineModel getMineData() {
        String json = sharePreference.read(MINE_DATA, "");
        if (TextUtils.isEmpty(json)) {
            return null;
        }
        return JSON.parseObject(json, MineModel.class);
    }

}
