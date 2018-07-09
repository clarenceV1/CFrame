package com.cai.work.ui.login;

import com.cai.work.base.AppBasePresenter;
import com.cai.work.bean.CandyList;
import com.cai.work.bean.PhoneCode;
import com.cai.work.bean.User;
import com.cai.work.bean.respond.CandyListRespond;
import com.cai.work.bean.respond.LoginRespond;
import com.cai.work.bean.respond.PhoneCodeRespond;
import com.cai.work.event.LoginEvent;
import com.cai.work.ui.candy.CandyDetailView;
import com.example.clarence.netlibrary.NetRespondCallBack;

import org.greenrobot.eventbus.EventBus;
import org.reactivestreams.Subscription;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;

public class LoginPresenter extends AppBasePresenter<LoginView> {
    @Inject
    public LoginPresenter() {
    }

    @Override
    public void onAttached() {

    }

    public void getPhoneCode(String nation_code, final String phone) {
        Map<String, String> params = new HashMap<>();
        params.put("nation_code", nation_code);
        params.put("phone", phone);
        requestStore.get().getPhoneCode(params)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new NetRespondCallBack<PhoneCodeRespond>() {
                    @Override
                    public void respondResult(Subscription subscription, PhoneCodeRespond respond) {
                        if (respond.getErrorcode() == 0) {
                            mView.callBack(respond.getData());
                        } else {
                            mView.callBack(new PhoneCode());
                            mView.callBack(respond.getMessage());
                        }
                    }

                    @Override
                    public void respondError(Throwable t) {
                        mView.callBack(t.getMessage());
                        mView.callBack(new PhoneCode());
                    }
                });
    }

    public void loginOrRegister(String nation_code, String phone, String vercode) {
        HashMap<String, String> params = new HashMap<>();
        params.put("nation_code", nation_code);
        params.put("phone", phone);
        params.put("vercode", vercode);
        requestStore.get().loginOrRegister(params)
                .doOnNext(new Consumer<LoginRespond>() {
                    @Override
                    public void accept(LoginRespond loginRespond) throws Exception {
                        if (loginRespond.getErrorcode() == 0) {
                            userDAO.get().save(loginRespond.getData());
                            EventBus.getDefault().post(new LoginEvent(LoginEvent.STATE_LOGIN_IN));
                        }
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new NetRespondCallBack<LoginRespond>() {
                    @Override
                    public void respondResult(Subscription subscription, LoginRespond respond) {
                        if (respond.getErrorcode() == 0) {
                            mView.callBack(respond.getData());
                        } else {
                            mView.callBack(respond.getMessage());
                        }
                    }

                    @Override
                    public void respondError(Throwable t) {
                        mView.callBack(t.getMessage());
                    }
                });
    }
}
