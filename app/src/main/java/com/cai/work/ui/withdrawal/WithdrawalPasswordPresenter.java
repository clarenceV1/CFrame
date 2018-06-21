package com.cai.work.ui.withdrawal;

import com.cai.framework.base.GodBasePresenter;
import com.cai.lib.logger.Logger;
import com.cai.work.R;
import com.cai.work.bean.respond.CommonRespond;
import com.cai.work.common.DataStore;
import com.cai.work.common.RequestStore;
import com.cai.work.dao.AccountDAO;
import com.cai.work.dao.UserDAO;
import com.example.clarence.utillibrary.Md5Utils;
import com.example.clarence.utillibrary.NetWorkUtil;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class WithdrawalPasswordPresenter extends GodBasePresenter<WithdrawalPasswordView> {

    @Inject
    RequestStore requestStore;
    @Inject
    DataStore dataStore;
    @Inject
    UserDAO userDAO;
    @Inject
    AccountDAO accountDAO;

    @Inject
    public WithdrawalPasswordPresenter() {
    }

    @Override
    public void onAttached() {

    }

    /**
     * 获取短信验证码
     */
    public void requestIdentifyCode(String mobile) {
        int type = 2;
        Disposable disposable = requestStore.requestIdentifyCode(mobile, type, new Consumer<CommonRespond>() {
            @Override
            public void accept(CommonRespond data) {
                if (data.getCode() == 200) {
                    String tostStr = context.getString(R.string.register_identify_code_send);
                    mView.toast(1, tostStr);
                } else {
                    mView.toast(2, data.getResponseText());
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

    public void resetWithdrawalPassword(String mobile, String sms, String loginOldPassword, String loginPassword) {
        String token = accountDAO.getToken();
        Disposable disposable = requestStore.resetWithdrawalPassword(mobile, sms, Md5Utils.md5(loginOldPassword), Md5Utils.md5(loginPassword), token, new Consumer<CommonRespond>() {
            @Override
            public void accept(CommonRespond data) {
                if (data.getCode() == 200) {
                    mView.toast(3, context.getString(R.string.reset_password_toast));
                } else {
                    mView.toast(4, data.getResponseText());
                }
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) {
                if (NetWorkUtil.isNetConnected(context)) {
                    Logger.d("请求用户信息数据失败！！！---有网络");
                } else {
                    Logger.d("请求用户信息数据失败！！！---没网络");
                }
            }
        });
        mCompositeSubscription.add(disposable);
    }

    public void getMobile() {
        mView.getMobile(accountDAO.getMobile());
    }
}