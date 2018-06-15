package com.cai.work.ui.login;

import com.cai.framework.base.GodBasePresenter;
import com.cai.lib.logger.Logger;
import com.cai.work.R;
import com.cai.work.bean.Account;
import com.cai.work.bean.respond.CommonRespond;
import com.cai.work.bean.respond.UserInfoRespond;
import com.cai.work.common.DataStore;
import com.cai.work.common.RequestStore;
import com.cai.work.dao.AccountDAO;
import com.cai.work.dao.UserDAO;
import com.example.clarence.utillibrary.Md5Utils;
import com.example.clarence.utillibrary.NetWorkUtil;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class RegisterPresenter extends GodBasePresenter<RegisterView> {

    @Inject
    RequestStore requestStore;
    @Inject
    DataStore dataStore;
    @Inject
    UserDAO userDAO;
    @Inject
    AccountDAO accountDAO;

    @Inject
    public RegisterPresenter() {
    }

    @Override
    public void onAttached() {

    }

    public void commitRegister(final String mobile, String sms, final String loginPassword, String invitationCode) {
        Disposable disposable = requestStore.requestRegister(mobile, sms, Md5Utils.md5(loginPassword), invitationCode, new Consumer<CommonRespond>() {
            @Override
            public void accept(CommonRespond data) {
                if (data.getCode() == 200) {
                    Account account = new Account();
                    account.setToken(data.getData());
                    account.setPassword(loginPassword);
                    account.setMobile(mobile);
                    accountDAO.save(account);
                } else {
                    mView.toast(data.getResponseText());
                }
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) {
                if (NetWorkUtil.isNetConnected(context)) {
                    Logger.d("请求注册数据失败！！！---有网络");
                } else {
                    Logger.d("请求注册数据失败！！！---没网络");
                }
            }
        });
        mCompositeSubscription.add(disposable);
    }


    /**
     * 获取短信验证码
     */
    public void requestIdentifyCode(String mobile) {
        int type = 1;
        Disposable disposable = requestStore.requestIdentifyCode(mobile, type, new Consumer<CommonRespond>() {
            @Override
            public void accept(CommonRespond data) {
                if (data.getCode() == 200) {
                    String tostStr = context.getString(R.string.register_identify_code_send);
                    mView.toast(tostStr);
                } else {
                    mView.toast(data.getResponseText());
                }
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) {
                if (NetWorkUtil.isNetConnected(context)) {
                    Logger.d("请求验证码数据失败！！！---有网络");
                } else {
                    Logger.d("请求验证码数据失败！！！---没网络");
                }
            }
        });
        mCompositeSubscription.add(disposable);
    }
}
