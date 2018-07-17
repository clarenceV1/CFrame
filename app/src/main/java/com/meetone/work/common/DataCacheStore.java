package com.meetone.work.common;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.example.clarence.datastorelibrary.store.share_preference.ISharePreference;
import com.meetone.work.bean.Asset;
import com.meetone.work.bean.CandyList;
import com.meetone.work.bean.Discover;
import com.meetone.work.bean.Message;
import com.meetone.work.bean.MineModel;
import com.meetone.work.bean.Welfare;

import java.util.List;

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
    private static final String CANDY_LIST_DATA = "candy_list_data";
    private static final String DISCOVER_LIST_DATA = "discover_list_data";
    private static final String MSG_LIST_DATA = "msg_list_data";
    private static final String WELFARE_DATA = "welfare_data";
    private static final String ASSET_DATA = "asset_data";

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

    public void saveCandyList(List<CandyList> candyList) {
        String json = JSON.toJSONString(candyList);
        sharePreference.write(CANDY_LIST_DATA, json);
    }

    public List<CandyList> getCandyList() {
        String json = sharePreference.read(CANDY_LIST_DATA, "");
        if (TextUtils.isEmpty(json)) {
            return null;
        }
        return JSON.parseArray(json, CandyList.class);
    }

    public void saveDiscoverList(List<Discover> candyList) {
        String json = JSON.toJSONString(candyList);
        sharePreference.write(DISCOVER_LIST_DATA, json);
    }

    public List<Discover> getDiscoverList() {
        String json = sharePreference.read(DISCOVER_LIST_DATA, "");
        if (TextUtils.isEmpty(json)) {
            return null;
        }
        return JSON.parseArray(json, Discover.class);
    }


    public void saveMsgList(List<Message> messageList) {
        String json = JSON.toJSONString(messageList);
        sharePreference.write(MSG_LIST_DATA, json);
    }

    public List<Message> getMsgList() {
        String json = sharePreference.read(MSG_LIST_DATA, "");
        if (TextUtils.isEmpty(json)) {
            return null;
        }
        return JSON.parseArray(json, Message.class);
    }

    public void saveWelfareList(List<Welfare> welfareList) {
        String json = JSON.toJSONString(welfareList);
        sharePreference.write(WELFARE_DATA, json);
    }

    public List<Welfare> getWelfareList() {
        String json = sharePreference.read(WELFARE_DATA, "");
        if (TextUtils.isEmpty(json)) {
            return null;
        }
        return JSON.parseArray(json, Welfare.class);
    }

    public void saveAssetList(List<Asset> assetList) {
        String json = JSON.toJSONString(assetList);
        sharePreference.write(ASSET_DATA, json);
    }

    public List<Asset> getAssetList() {
        String json = sharePreference.read(ASSET_DATA, "");
        if (TextUtils.isEmpty(json)) {
            return null;
        }
        return JSON.parseArray(json, Asset.class);
    }
}