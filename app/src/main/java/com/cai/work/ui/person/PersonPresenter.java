package com.cai.work.ui.person;

import com.cai.work.base.AppBasePresenter;
import com.cai.work.bean.Message;
import com.cai.work.bean.PhoneCode;
import com.cai.work.bean.User;
import com.cai.work.bean.respond.MessageRespond;
import com.cai.work.bean.respond.MineRespond;
import com.cai.work.bean.respond.NicknameRespond;
import com.cai.work.bean.respond.PhoneCodeRespond;
import com.cai.work.dao.UserDAO;
import com.cai.work.ui.message.MessageView;
import com.example.clarence.netlibrary.NetRespondCallBack;

import org.reactivestreams.Subscription;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
        Map<String, String> params = new HashMap<>();
        params.put("avatar", "");
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
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.callBack(throwable.getMessage());
                    }
                });
        mCompositeSubscription.add(disposable);
    }
}
