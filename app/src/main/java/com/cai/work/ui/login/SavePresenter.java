package com.cai.work.ui.login;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;

import com.cai.framework.base.GodBasePresenter;
import com.cai.lib.logger.Logger;
import com.cai.work.bean.User;
import com.cai.work.bean.respond.UploadRespond;
import com.cai.work.common.DataStore;
import com.cai.work.common.RequestStore;
import com.cai.work.dao.AccountDAO;
import com.cai.work.dao.UserDAO;
import com.example.clarence.utillibrary.NetWorkUtil;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

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
    public void uploadImage(final Context context, final String path) {
        Observable.create(new ObservableOnSubscribe<Bitmap>() {
            @Override
            public void subscribe(ObservableEmitter<Bitmap> result) {
//                String path = UriUtils.getFilePathByUri(context, uri);
//                String imageBase64 = getImageData(path);
//                if (TextUtils.isEmpty(imageBase64)) {
//                    imageBase64 = "";
//                }
//                result.onNext(imageBase64);
                Bitmap bitmap = getImageData(path);
                result.onNext(bitmap);
            }
        }).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Bitmap>() {
                    @Override
                    public void accept(Bitmap imageData) {
                        mView.showHeadImg(imageData);
//                        uploadUserHeadImg(imageData);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) {
                        Logger.e(throwable.getMessage());
                    }
                });
    }

    private Bitmap getImageData(String path) {
        if (TextUtils.isEmpty(path)) {
            return null;
        }
        try {
            FileInputStream fis = new FileInputStream(path);
            Bitmap bitmap = BitmapFactory.decodeStream(fis);
            return bitmap;
//            byte[] bytes = ImageUtils.bitmap2Bytes(bitmap);
//            String data = Md5Utils.md5(bytes);
//            return data;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
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
