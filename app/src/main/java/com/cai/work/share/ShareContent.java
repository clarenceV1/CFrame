package com.cai.work.share;

import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.onekeyshare.ShareContentCustomizeCallback;

public class ShareContent implements ShareContentCustomizeCallback {

    public void onShare(Platform platform, Platform.ShareParams paramsToShare) {
        if (Wechat.NAME.equals(platform.getName())) {  //微信平台

        } else if (QQ.NAME.equals(platform.getName())) {

        }
    }

}
