package com.cai.work.common;

import android.content.Context;

import java.io.File;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class FileStore {
    Context context;

    @Inject
    public FileStore(Context context) {
        this.context = context;
    }

    private String getCachePath() {
        return context.getCacheDir().getPath() + "/";
    }

    private String getQRcodePath() {
        return getCachePath() + "_QR_Code";
    }

    /**
     * 当前用户的分享二维码路径
     *
     * @return
     */
    public File getQRcodeFile(long userId) {
        File rootFile = new File(getQRcodePath());
        if (!rootFile.exists()) {
            rootFile.mkdirs();
        }
        return new File(rootFile, getQRcodeName(userId));
    }

    public static String getQRcodeName(long userId) {
        return "qr_code_" + userId + ".jpg";
    }
}
