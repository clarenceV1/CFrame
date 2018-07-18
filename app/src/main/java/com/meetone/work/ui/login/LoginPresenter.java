package com.meetone.work.ui.login;

import com.alibaba.fastjson.JSON;
import com.cai.framework.http.NetRespondCallBack;
import com.meetone.work.base.AppBasePresenter;
import com.meetone.work.bean.PhoneCode;
import com.meetone.work.bean.respond.LoginRespond;
import com.meetone.work.bean.respond.Respond;
import com.meetone.work.event.LoginEvent;

import org.greenrobot.eventbus.EventBus;
import org.reactivestreams.Subscription;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import okhttp3.ResponseBody;

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
                .subscribe(new NetRespondCallBack<ResponseBody>() {
                    @Override
                    public void respondResult(Subscription subscription, ResponseBody responseBody) {
                        PhoneCode phoneCode = new PhoneCode();
                        try {
                            String resultStr = responseBody.string();
                            Respond respond = JSON.parseObject(resultStr, Respond.class);
                            if (respond != null) {
                                if (respond.getErrorcode() == 0) {
                                    phoneCode = JSON.parseObject(respond.getData(), PhoneCode.class);
                                } else {
                                    mView.callBack(respond.getMessage());
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            mView.callBack(e.getMessage());
                        }
                        mView.callBack(phoneCode);
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
