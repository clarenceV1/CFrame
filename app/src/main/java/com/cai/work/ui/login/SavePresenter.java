package com.cai.work.ui.login;

import com.cai.framework.base.GodBasePresenter;
import com.cai.work.bean.Account;
import com.cai.work.bean.User;
import com.cai.work.common.DataStore;
import com.cai.work.common.RequestStore;
import com.cai.work.dao.AccountDAO;
import com.cai.work.dao.UserDAO;
import com.cai.work.ui.rank.RankView;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
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
}
