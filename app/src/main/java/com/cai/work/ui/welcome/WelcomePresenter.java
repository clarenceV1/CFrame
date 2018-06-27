package com.cai.work.ui.welcome;

import android.annotation.SuppressLint;

import com.alibaba.fastjson.JSON;
import com.cai.work.R;
import com.cai.work.base.App;
import com.cai.work.base.AppBasePresenter;
import com.cai.work.bean.MineModel;
import com.cai.work.bean.MineUserModel;
import com.cai.work.bean.respond.AppUpdateResond;
import com.cai.work.bean.respond.MineRespond;
import com.cai.work.qrcode.QRCodeCreat;

import org.reactivestreams.Subscription;

import java.io.File;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

/**
 * Created by clarence on 2018/1/12.
 */
public class WelcomePresenter extends AppBasePresenter<WelcomeView> {


    @Inject
    public WelcomePresenter() {

    }

    @Override
    public void onAttached() {
        App.getAppComponent().inject(this);
    }

    public void loadUpgrade() {
//        Flowable<AppUpdateResond> flowable = requestStore.get().loadUpgrade();
//        flowable.doAfterNext(new Consumer<AppUpdateResond>() {
//            @Override
//            public void accept(AppUpdateResond o){
//
//            }
//        });
//        flowable.subscribe(new Consumer<AppUpdateResond>() {
//            @Override
//            public void accept(AppUpdateResond data) {
//                dataStore.get().saveAppUpdate(JSON.toJSONString(data));
//                mView.appUpdate();
//            }
//        }, new Consumer<Throwable>() {
//            @Override
//            public void accept(Throwable throwable) {
//                mView.appUpdate();
//            }
//        });
//        mCompositeSubscription.add(disposable);
    }

    public void loadMineData() {
        Disposable disposable = requestStore.get().loadMineData(new Consumer<MineRespond>() {
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
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) {
            }
        });
        mCompositeSubscription.add(disposable);
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
}
