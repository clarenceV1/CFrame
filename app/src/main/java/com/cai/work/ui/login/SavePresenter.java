package com.cai.work.ui.login;

import android.annotation.SuppressLint;

import com.cai.framework.base.GodBasePresenter;
import com.cai.lib.logger.Logger;
import com.cai.work.bean.User;
import com.cai.work.bean.respond.UploadRespond;
import com.cai.work.common.DataStore;
import com.cai.work.common.RequestStore;
import com.cai.work.dao.AccountDAO;
import com.cai.work.dao.UserDAO;
import com.example.clarence.utillibrary.Base64Utils;
import com.example.clarence.utillibrary.NetWorkUtil;

import java.io.File;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class SavePresenter extends GodBasePresenter<SaveView> {

    @Inject
    RequestStore requestStore;
    @Inject
    DataStore dataStore;
    @Inject
    UserDAO userDAO;
    @Inject
    AccountDAO accountDAO;

    @Inject
    public SavePresenter() {
    }

    @Override
    public void onAttached() {

    }

    public User getUserInfo() {
        return userDAO.getData();
    }

    @SuppressLint("CheckResult")
    public void quit() {
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> result) {
                userDAO.deleteAll();
                accountDAO.deleteAll();
                result.onNext("");
            }
        }).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String dataList) {
                        mView.loginOut();
                    }
                });
    }

    @SuppressLint("CheckResult")
    public void uploadImage(final String path) {
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> result) {
                String imageBase64 = Base64Utils.encodeToFile(new File(path));
                result.onNext(imageBase64);
            }
        }).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String imageData) {
                        uploadUserHeadImg(imageData);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) {
                        Logger.e(throwable.getMessage());
                    }
                });
    }

    /**
     * 上传用户头像
     *
     * @return
     */
    public void uploadUserHeadImg(String imageBase64) {
        String token = accountDAO.getToken();
        Disposable disposable = requestStore.uploadUserHeadImg(token, "avatar", imageBase64, new Consumer<UploadRespond>() {
            @Override
            public void accept(UploadRespond data) {
                if (data != null && data.getCode() == 200) {
                    Logger.d("uploadImage===>" + data.getData());
                    mView.showHeadImg(data.getData());
                }
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) {
                if (NetWorkUtil.isNetConnected(context)) {
                    Logger.d("请求login数据失败！！！---有网络");
                } else {
                    Logger.d("请求login数据失败！！！---没网络");
                }
            }
        });
        mCompositeSubscription.add(disposable);
    }
}
