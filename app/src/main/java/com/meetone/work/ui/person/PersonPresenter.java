package com.meetone.work.ui.person;

import android.util.Log;

import com.example.clarence.utillibrary.StringUtils;
import com.meetone.work.base.AppBasePresenter;
import com.meetone.work.bean.User;
import com.meetone.work.bean.respond.NicknameRespond;
import com.meetone.work.event.LoginEvent;
import com.meetone.work.event.UserInfoUpdateEvent;
import com.meetone.work.qinius.QiNiuController;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by clarence on 2018/1/12.
 */
public class PersonPresenter extends AppBasePresenter<PersonView> {

    @Inject
    QiNiuController qiNiuController;

    @Inject
    public PersonPresenter() {

    }

    @Override
    public void onAttached() {

    }

    public void getUserInfo() {
        Disposable disposable = Observable.create(new ObservableOnSubscribe<User>() {
            @Override
            public void subscribe(ObservableEmitter<User> e) {
                User user = userDAO.get().getUser();
                if (user == null) {
                    user = new User();
                }
                e.onNext(user);
            }
        }).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<User>() {
                    @Override
                    public void accept(User user) {
                        mView.callBack(user);
                    }
                });
        mCompositeSubscription.add(disposable);
    }

    public void upUserNickName(final String nickname) {
        uploadUserInfo("", nickname);
    }

    public void uploadUserInfo(String avatar, String nickname) {
        Map<String, String> params = new HashMap<>();
        params.put("avatar", avatar);
        params.put("nickname", nickname);
        Disposable disposable = requestStore.get().upUserNickName(params)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Consumer<NicknameRespond>() {
                    @Override
                    public void accept(NicknameRespond respond) {
                        if (respond.getErrorcode() == 0) {
                            User user = respond.getData();
                            userDAO.get().update(user.getUser_id()
                                    , user.getAvatar(), user.getNation_code()
                                    , user.getNickname(), user.getPhone());
                        }
                    }
                })
                .subscribe(new Consumer<NicknameRespond>() {
                    @Override
                    public void accept(NicknameRespond respond) {
                        if (respond.getErrorcode() == 0) {
                            User user = respond.getData();
                            mView.callBack(user);
                        } else {
                            mView.callBack(respond.getMessage());
                        }
                        EventBus.getDefault().post(new UserInfoUpdateEvent());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.callBack(throwable.getMessage());
                    }
                });
        mCompositeSubscription.add(disposable);
    }

    public void upUserHead(String filePath) {
        String key = "MoreOne/image/Android/avatar_" + System.currentTimeMillis() + "." + StringUtils.getSuffixName(filePath);
        String token = dataStore.get().getQiniuToken();
        qiNiuController.uploadManager(filePath, key, token, new UpCompletionHandler() {
            @Override
            public void complete(String key, ResponseInfo info, JSONObject response) {
                Log.e("upUserHead", "ResponseInfo =" + info.toString());
                if (info.isOK()) {
                    uploadUserInfo(key, "");
                } else {
                    mView.callBack("上传错误");
                }
            }
        });
    }

    public void loginOut() {
        Disposable disposable = Observable.create(new ObservableOnSubscribe<User>() {
            @Override
            public void subscribe(ObservableEmitter<User> e) throws Exception {
                userDAO.get().loginOut();
                e.onNext(new User());

            }
        }).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<User>() {
                    @Override
                    public void accept(User user) throws Exception {
                        mView.loginout();
                        EventBus.getDefault().post(new LoginEvent(LoginEvent.STATE_LOGIN_OUT));
                    }
                });
        mCompositeSubscription.add(disposable);

    }
}
