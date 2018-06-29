package com.cai.work.common;

import android.net.Uri;
import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.cai.work.bean.AppUpdate;
import com.example.clarence.datastorelibrary.store.share_preference.ISharePreference;
import com.example.clarence.utillibrary.encrypt.CipherUtil;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by clarence on 2018/3/26.
 */
@Singleton
public class DataStore {
    @Inject
    ISharePreference sharePreference;

    private static final String AUTHORIZATION_KEY = "authorization_key";
    private static final String APP_LANGUAGE = "app_language";
    private static final String APP_UPDATE = "app_update";
    private static final String INVITE_TITLE = "invite_title";
    private static final String INVITE_URL = "invite_url";
    private static final String SHORT_CUT = "short_cut";

    @Inject
    public DataStore() {

    }

    public void setAuthorization(String authorization) {
        String aseAuthorization = CipherUtil.aesEncrypt(authorization);
        sharePreference.write(AUTHORIZATION_KEY, aseAuthorization);
    }

    public String getAuthorization() {
        String aseAuthorization = sharePreference.read(AUTHORIZATION_KEY, "");
        String authorization = CipherUtil.aesDecrypt(aseAuthorization);
        if (authorization != null && authorization.trim().isEmpty()) {
            authorization = Uri.encode(authorization);
        }
        return authorization == null ? "" : authorization;
    }

    public void saveAppUpdate(String json) {
        sharePreference.write(APP_UPDATE, json);
    }

    public AppUpdate getAppUpdate() {
        String json = sharePreference.read(APP_UPDATE, null);
        if (!TextUtils.isEmpty(json)) {
            return JSON.parseObject(json, AppUpdate.class);
        }
        return null;
    }

    /**
     * 保存语言
     *
     * @param language
     */
    public void setLanguage(String language) {
        sharePreference.write(APP_LANGUAGE, language);
    }

    public String getLanguage() {
        return sharePreference.read(APP_LANGUAGE, "zh");
    }

    public void saveInviteTitle(String invitetitle) {
        sharePreference.write(INVITE_TITLE, invitetitle);
    }

    public String getInviteTitle() {
        return sharePreference.read(INVITE_TITLE, null);
    }

    public void saveInviteUrl(String inviteUrl) {
        sharePreference.write(INVITE_URL, inviteUrl);
    }

    public String getInviteUrl() {
        return sharePreference.read(INVITE_URL, null);
    }

    public void saveShortCut() {
        sharePreference.write(SHORT_CUT, true);
    }

    public boolean getShortCut() {
        return sharePreference.read(SHORT_CUT, false);
    }
}
