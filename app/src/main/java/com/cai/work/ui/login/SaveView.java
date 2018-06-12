package com.cai.work.ui.login;

import android.graphics.Bitmap;

import com.cai.framework.base.GodBaseView;

/**
 * Created by clarence on 2018/1/12.
 */

public interface SaveView extends GodBaseView {
    void loginOut();

    void showHeadImg(String image);
    void showHeadImg(Bitmap image);
}
