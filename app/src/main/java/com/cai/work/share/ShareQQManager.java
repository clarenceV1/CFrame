package com.cai.work.share;

import android.content.Context;
import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cai.framework.web.IWebProtocolCallback;
import com.cai.framework.web.WebProtocolDO;
import com.cai.work.base.StateCode;
import com.cai.work.utils.ShareUtil;
import com.example.clarence.utillibrary.PackageUtils;

import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.tencent.qq.QQ;

public class ShareQQManager {

    public static void shareQQForProtocol(final Context context, final WebProtocolDO protocolDO, final IWebProtocolCallback callback) {
        if(!PackageUtils.isAppInstall(context, ShareUtil.QQ)){
            callback.callBack(context,protocolDO, StateCode.STATE_CODE_NO_INSTALL, "客户端没有安装QQ", "分享出错");
            return;
        }
        shareQQ(protocolDO.getParams(), new PlatformActionListener() {
            @Override
            public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
                callback.callBack(context,protocolDO,StateCode.STATE_CODE_SUCCESS, "", "分享成功");
            }

            @Override
            public void onError(Platform platform, int i, Throwable throwable) {
                callback.callBack(context,protocolDO,StateCode.STATE_CODE_FAIL, throwable.getMessage(), "分享出错");
            }

            @Override
            public void onCancel(Platform platform, int i) {
                callback.callBack(context,protocolDO,StateCode.STATE_CODE_CANCLE, "", "分享取消");
            }
        });
    }

    public static void shareQQ(String parama, PlatformActionListener shareCallback) {
        if (TextUtils.isEmpty(parama)) {
            return;
        }
        JSONObject jsonObject = JSON.parseObject(parama);
        String title = jsonObject.getString("title");
        String description = jsonObject.getString("description");
        String link = jsonObject.getString("link");
        String imgUrl = jsonObject.getString("imgUrl");
        int shareType = jsonObject.getIntValue("shareType");

        Platform platform = ShareSDK.getPlatform(QQ.NAME);
        Platform.ShareParams shareParams = new Platform.ShareParams();

        if(!TextUtils.isEmpty(title)){
            shareParams.setTitle(title);
        }else{
            shareParams.setTitle("meetone");
        }
        if(!TextUtils.isEmpty(description)){
            shareParams.setText(description);
        }else{
            shareParams.setText("meetone");
        }
        if(!TextUtils.isEmpty(link)){
            shareParams.setTitleUrl(link);
        }else{
            shareParams.setTitleUrl("https:meet.one");
        }
        shareParams.setImageUrl(imgUrl);
        shareParams.setShareType(shareType);
        platform.setPlatformActionListener(shareCallback);
        platform.share(shareParams);
    }
}
