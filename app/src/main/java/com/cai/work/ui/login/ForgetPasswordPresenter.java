package com.cai.work.ui.login;

import com.cai.framework.base.GodBasePresenter;
import com.cai.lib.logger.Logger;
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

public class ForgetPasswordPresenter extends GodBasePresenter<LoginView> {

    @Inject
    RequestStore requestStore;
    @Inject
    DataStore dataStore;
    @Inject
    UserDAO userDAO;
    @Inject
    AccountDAO accountDAO;

    @Inject
    public ForgetPasswordPresenter() {
    }

    @Override
    public void onAttached() {

    }

    /**
     * 请求登录
     *
     * @return
     */
    public void requestLogin(final String userName, String password, boolean isSavePassword) {
        Disposable disposable = requestStore.requestLogin(userName, Md5Utils.md5(password), new Consumer<LoginRespond>() {
            @Override
            public void accept(LoginRespond data) {
                if (data != null && data.getCode() == 200) {
                    accountDAO.updateToken(userName,data.getData());
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
                userDAO.save(data.getData());
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
