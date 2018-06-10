package com.cai.work.ui.login;

import com.cai.framework.base.GodBasePresenter;
import com.cai.lib.logger.Logger;
import com.cai.work.bean.respond.BankListRespond;
import com.cai.work.bean.respond.BaseRespond;
import com.cai.work.bean.respond.LoginRespond;
import com.cai.work.bean.respond.UserInfoRespond;
import com.cai.work.common.DataStore;
import com.cai.work.common.RequestStore;
import com.cai.work.dao.AccountDAO;
import com.cai.work.ui.bank.AddBankCardView;
import com.example.clarence.utillibrary.Md5Utils;
import com.example.clarence.utillibrary.NetWorkUtil;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class LoginPresenter extends GodBasePresenter<LoginView> {

    @Inject
    RequestStore requestStore;
    @Inject
    DataStore dataStore;
    @Inject
    AccountDAO accountDAO;

    @Inject
    public LoginPresenter() {
    }

    @Override
    public void onAttached() {

    }

    /**
     * 请求登录
     *
     * @return
     */
    private void requestLogin(String userName, String password) {
        Disposable disposable = requestStore.requestLogin(userName, Md5Utils.md5(password), new Consumer<LoginRespond>() {
            @Override
            public void accept(LoginRespond data) {
                if (data != null && data.getCode() == 200) {
                    dataStore.setToken(data.getData());
                    requestUserInfo(data.getData());
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
    /**
     * 请求用户数据
     * @param token
     * @return
     */
    private void requestUserInfo(String token) {
        Disposable disposable = requestStore.requestUserInfo(token, new Consumer<UserInfoRespond>() {
            @Override
            public void accept(UserInfoRespond data) {
                accountDAO.save(data.getData());
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

}
