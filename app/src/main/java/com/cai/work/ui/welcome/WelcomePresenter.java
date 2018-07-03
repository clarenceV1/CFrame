package com.cai.work.ui.welcome;

import android.Manifest;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.cai.work.R;
import com.cai.work.base.App;
import com.cai.work.base.AppBasePresenter;
import com.cai.work.bean.MineModel;
import com.cai.work.bean.MineUserModel;
import com.cai.work.bean.respond.AppUpdateResond;
import com.cai.work.bean.respond.MineRespond;
import com.cai.work.qrcode.QRCodeCreat;
import com.example.clarence.netlibrary.NetRespondNoCallBack;
import com.example.clarence.utillibrary.ShortCutUtils;

import java.io.File;

import javax.inject.Inject;

import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

/**
 * Created by clarence on 2018/1/12.
 */
public class WelcomePresenter extends AppBasePresenter<WelcomeView> {

    @Inject
    public WelcomePresenter() {

    }

    @Override
    public void onAttached() {

    }

    public void loadUpgrade() {
        requestStore.get().loadUpgrade()
                .map(new Function<AppUpdateResond, String>() {
                    @Override
                    public String apply(AppUpdateResond appUpdateResond) {
                        if (appUpdateResond.getData() != null) {
                            return JSON.toJSONString(appUpdateResond.getData());
                        }
                        return "";
                    }
                }).doOnNext(new Consumer<String>() {
            @Override
            public void accept(String jsonStr) {
                if (!TextUtils.isEmpty(jsonStr)) {
                    dataStore.get().saveAppUpdate(jsonStr);
                }
            }
        }).subscribe(new NetRespondNoCallBack<String>());
    }

    public void loadMineData() {
        requestStore.get().loadMineData().doOnNext(new Consumer<MineRespond>() {
            @Override
            public void accept(MineRespond data) {
                MineModel mineModel = data.getData();
                if (mineModel != null) {
                    MineUserModel mineUserModel = mineModel.getUser();
                    if (mineUserModel != null) {
                        userDAO.get().update(mineUserModel.getUser_id(),
                                mineUserModel.getAvatar(),
                                mineUserModel.getNation_code(),
                                mineUserModel.getNickname(),
                                mineUserModel.getPhone());
                    }
                    cacheStore.get().saveMineData(mineModel);
                    dataStore.get().saveInviteTitle(mineModel.getInvitetitle());
                    dataStore.get().saveInviteUrl(mineModel.getInviteurl());
                    createQRcode(mineModel);
                }
            }
        }).subscribe(new NetRespondNoCallBack<>());
    }

    //二维码生成
    private void createQRcode(MineModel mineModel) {
        try {
            File file = fileStore.get().getQRcodeFile(1);
            String inviteUrl = mineModel.getInviteurl();
            QRCodeCreat.createLogoQRImage(inviteUrl, 270, null, QRCodeCreat.resourceToBitmap(context, R.drawable.qcode_bg), file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void createShortCut() {
        if (!dataStore.get().getShortCut()) {
            ShortCutUtils.creatShortCut(context, context.getResources().getString(R.string.app_name), R.drawable.ic_launcher, WelcomeActivity.class);
            dataStore.get().saveShortCut();
        }
    }

}
