package com.meetone.work.ui.welcome;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.meetone.work.R;
import com.example.clarence.netlibrary.NetRespondNoCallBack;
import com.example.clarence.utillibrary.ShortCutUtils;
import com.meetone.work.base.AppBasePresenter;
import com.meetone.work.bean.MineModel;
import com.meetone.work.bean.User;
import com.meetone.work.bean.respond.AppUpdateResond;
import com.meetone.work.bean.respond.MineRespond;
import com.meetone.work.qrcode.QRCodeCreat;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
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
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new NetRespondNoCallBack<>());
    }

    public void loadMineData() {
        requestStore.get().loadMineData().doOnNext(new Consumer<MineRespond>() {
            @Override
            public void accept(MineRespond data) {
                MineModel mineModel = data.getData();
                if (mineModel != null) {
                    User user = mineModel.getUser();
                    if (user != null) {
                        userDAO.get().update(user.getUser_id(),
                                user.getAvatar(),
                                user.getNation_code(),
                                user.getNickname(),
                                user.getPhone());
                    }
                    cacheStore.get().saveMineData(mineModel);
                    dataStore.get().saveInviteTitle(mineModel.getInvitetitle());
                    dataStore.get().saveInviteUrl(mineModel.getInviteurl());
                    QRCodeCreat.createQRcode(context,fileStore.get().getQRcodeFile(1),mineModel.getInviteurl());
                }
            }
        }).observeOn(AndroidSchedulers.mainThread())
        .subscribe(new NetRespondNoCallBack<>());
    }

    public void createShortCut() {
        if (!dataStore.get().getShortCut()) {
            ShortCutUtils.creatShortCut(context, context.getResources().getString(R.string.app_name), R.drawable.ic_launcher, WelcomeActivity.class);
            dataStore.get().saveShortCut();
        }
    }

}
