package com.cai.work.utils;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.cai.work.R;
import com.example.clarence.utillibrary.PackageUtils;
import com.example.clarence.utillibrary.ToastUtils;


/**
 * Created by davy on 2018/3/7.
 */

public class ShareUtil {

    public static final String QQ = "com.tencent.mobileqq";
    public static final String WX = "com.tencent.mm";
    public static final String SINA = "com.sina.weibo";


    public static void shareToQQ(Context context, String shareText) {
        if (checkAppIsInstall(context,QQ)) {
            share(context,QQ, shareText);
        }

    }

    public static void shareToWeiXin(Context context, String shareText) {
        if (checkAppIsInstall(context,WX)) {
            share(context,WX, shareText);
        }
    }

    public static void shareToSina(Context context, String shareText) {
        if (checkAppIsInstall(context,SINA)) {
            share(context,SINA, shareText);
        }
    }

    public static boolean checkAppIsInstall(Context context, String pageName) {
        boolean isInstall = PackageUtils.isAppInstall(context, pageName);
        if (!isInstall) {
            switch (pageName) {
                case QQ:
                    ToastUtils.showShort(R.string.qq_uninstall);
                    break;
                case WX:
                    ToastUtils.showShort(R.string.wx_uninstall);
                    break;
                case SINA:
                    ToastUtils.showShort(R.string.sina_uninstall);
                    break;
            }
        }
        return isInstall;
    }

    private static void share(Context context, String pakName, String shareText) {
        try {
            if (!TextUtils.isEmpty(shareText)) {
                Intent intent = new Intent(Intent.ACTION_SEND); // 启动分享发送的属性
                intent.setType("text/plain"); // 分享发送的数据类型
                intent.setPackage(pakName);
                intent.putExtra(Intent.EXTRA_TEXT, shareText); // 分享的内容
                context.startActivity(Intent.createChooser(intent, ""));// 目标应用选择对话框的标题;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
