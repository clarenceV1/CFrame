package com.cai.work.share;

import android.content.Context;

import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.onekeyshare.OnekeyShare;
/**
 * 一键分享管理
 */
public class ShareOneKeyManager {

    public static void showShare(Context context, PlatformActionListener shareCallback) {
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();
        // title标题，印象笔记、邮箱、信息、微信、人人网,QQ和QQ空间使用
        oks.setTitle("微信分享测试");
        // titleUrl QQ和QQ空间跳转链接
        oks.setTitleUrl("https://meet.one");
        // text是分享文本，所有平台都需要这个字段
        oks.setText("我是分享文本");
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
//        oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        String path = "http://f.hiphotos.baidu.com/image/pic/item/b219ebc4b74543a977adc9d01d178a82b9011473.jpg";
        oks.setImageUrl(path);
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        // oks.setImagePath("/sdcard/test.jpg");
        // url在微信、微博，Facebook等平台中使用
        oks.setUrl("https://meet.one");
        // comment是我对这条分享的评论，仅在人人网使用
        oks.setComment("我是测试评论文本");
        oks.setShareContentCustomizeCallback(new ShareContent());
        oks.setCallback(shareCallback);
        // 启动分享GUI
        oks.show(context);
    }
}
