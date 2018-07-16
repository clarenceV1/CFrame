package com.cai.work.ui.login;

import com.cai.framework.base.GodBasePresenter;
import com.cai.lib.logger.Logger;
import com.cai.work.bean.Account;
import com.cai.work.bean.respond.LoginRespond;
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

public class LoginPresenter extends GodBasePresenter<LoginView> {

    @Inject
    RequestStore requestStore;
    @Inject
    DataStore dataStore;
    @Inject
    UserDAO userDAO;
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
    public void requestLogin(final String userName, final String password, final boolean isSavePassword) {
        Disposable disposable = requestStore.requestLogin(userName, Md5Utils.md5(password), new Consumer<LoginRespond>() {
            @Override
            public void accept(LoginRespond data) {
                if (data != null && data.getCode() == 200) {
                    Account account = new Account();
                    account.setMobile(userName);
                    if (isSavePassword) {
                        account.setPassword(password);
                    }
                    account.setToken(data.getData());
                    accountDAO.save(account);
                    requestUserInfo(data.getData());
                }else{
                    mView.toast(1,data.getResponseText());
                }
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) {
                if (NetWorkUtil.isNetConnected(context)) {
                    mView.toast(1,throwable.getMessage());
                } else {
                    mView.toast(1,"抱歉！登陆失败，请检查您的网络");
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
                userDAO.save(data.getData());
                mView.loginSuccess();
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
