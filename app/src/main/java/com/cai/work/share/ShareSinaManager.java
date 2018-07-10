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

import cn.sharesdk.sina.weibo.SinaWeibo;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;

public class ShareSinaManager {


    public static void shareSinaForProtocol(final Context context, final WebProtocolDO WebProtocolDO, final IWebProtocolCallback callback) {
        if (!PackageUtils.isAppInstall(context,ShareUtil.SINA)) {
            callback.callBack(context,WebProtocolDO, StateCode.STATE_CODE_NO_INSTALL, "客户端没有安装新浪微博", "分享出错");
            return;
        }
        shareSina(WebProtocolDO.getParams(), new PlatformActionListener() {
            @Override
            public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
                callback.callBack(context,WebProtocolDO, StateCode.STATE_CODE_SUCCESS, "", "分享成功");
            }

            @Override
            public void onError(Platform platform, int i, Throwable throwable) {
                callback.callBack(context,WebProtocolDO, StateCode.STATE_CODE_FAIL, throwable.getMessage(), "分享出错");
            }

            @Override
            public void onCancel(Platform platform, int i) {
                callback.callBack(context,WebProtocolDO, StateCode.STATE_CODE_CANCLE, "", "分享取消");
            }
        });
    }

    /**
     * 新浪微博支持分享文字、本地图片、网络图片 参数说明 text：140字符以内image：web分享ShareSDK不做限制直接提交给微博处理，
     * 微博客户端分享图片不能大于2M，仅支持JPEG、GIF、PNG格式 ；注：微博分享链接是将链接写到setText内：
     * eg：setText(“分享文本 http://mob.com”);
     * 如果imagePath和imageUrl同时存在，imageUrl将被忽略。
     * <p>
     * 注意：分享本地视频必须用新浪微博客户端分享；
     * 分享本地视频	setFilePath(“/sdcard/video.MP4”)
     *
     * @param parama
     * @param shareCallback
     */
    public static void shareSina(String parama, PlatformActionListener shareCallback) {
        if (TextUtils.isEmpty(parama)) {
            return;
        }
        JSONObject jsonObject = JSON.parseObject(parama);
        String title = jsonObject.getString("title");
        String description = jsonObject.getString("description");
        String link = jsonObject.getString("link");
        String imgUrl = jsonObject.getString("imgUrl");
        int shareType = jsonObject.getIntValue("shareType");

        Platform platform = ShareSDK.getPlatform(SinaWeibo.NAME);
        Platform.ShareParams shareParams = new Platform.ShareParams();

        if (!TextUtils.isEmpty(title)) {
            shareParams.setTitle(title);
        } else {
            shareParams.setTitle("meetone");
        }
        if (!TextUtils.isEmpty(description)) {
            shareParams.setText(description);
        } else {
            shareParams.setText("meetone");
        }
        if (!TextUtils.isEmpty(link)) {
            shareParams.setUrl(link);
        } else {
            shareParams.setUrl("https:meet.one");
        }
        shareParams.setImageUrl(imgUrl);
        shareParams.setShareType(shareType);
        platform.setPlatformActionListener(shareCallback);
        platform.share(shareParams);
    }

}
