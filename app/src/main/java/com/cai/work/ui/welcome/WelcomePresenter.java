package com.cai.work.ui.welcome;

import com.alibaba.fastjson.JSON;
import com.cai.framework.base.GodBasePresenter;
import com.cai.lib.logger.Logger;
import com.cai.work.R;
import com.cai.work.bean.Account;
import com.cai.work.bean.HomeDataSql;
import com.cai.work.bean.respond.HomeRespond;
import com.cai.work.bean.respond.LoginRespond;
import com.cai.work.bean.respond.UserInfoRespond;
import com.cai.work.common.DataStore;
import com.cai.work.common.RequestStore;
import com.cai.work.dagger.component.DaggerAppComponent;
import com.cai.work.dao.AccountDAO;
import com.cai.work.dao.UserDAO;
import com.cai.work.dao.HomeDataSqlDAO;
import com.example.clarence.utillibrary.Md5Utils;
import com.example.clarence.utillibrary.NetWorkUtil;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by clarence on 2018/1/12.
 */
public class WelcomePresenter extends GodBasePresenter<WelcomeView> {
    @Inject
    DataStore dataStore;
    @Inject
    RequestStore requestStore;
    @Inject
    HomeDataSqlDAO homeDataSqlDAO;
    @Inject
    UserDAO userDAO;
    @Inject
    AccountDAO accountDAO;

    private int requestNum = 0;//已经请求过到数据个数
    private int request_all_num = 1;//  所要请求到个数

    @Inject
    public WelcomePresenter() {

    }

    @Override
    public void onAttached() {
        DaggerAppComponent.create().inject(this);
    }

    public void loadData() {
        if (!NetWorkUtil.isNetConnected(context)) {
            mView.toastNotice(context.getResources().getString(R.string.no_net));
            mView.goMainActivity();
            return;
        }
        Account account = accountDAO.getAccount();
        if (account != null) {
            request_all_num++;
            requestLogin(account);
        }
        requestHomeData();
    }

    /**
     * 请求首页数据
     *
     * @return
     */
    private void requestHomeData() {
        Disposable disposable = requestStore.requestHomeData(new Consumer<HomeRespond>() {
            @Override
            public void accept(HomeRespond data) {
                if (data != null) {
                    HomeDataSql homeDataSql = new HomeDataSql();
                    if (data.getCode() == 200 && data.getData() != null) {
                        homeDataSql.setData(JSON.toJSONString(data.getData()));
                        homeDataSqlDAO.saveHomeData(homeDataSql);
                        Logger.d("请求首页数据完成！！！");
                    } else {
                        Logger.d("请求首页数据有问题！！！");
                    }
                }
                requestAllEnd();
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) {
                if (NetWorkUtil.isNetConnected(context)) {
                    Logger.d("请求首页数据失败！！！---有网络");
                } else {
                    Logger.d("请求首页数据失败！！！---没网络");
                }
                requestAllEnd();
            }
        });
        mCompositeSubscription.add(disposable);
    }

    /**
     * 请求登录
     *
     * @return
     */
    private void requestLogin(final Account account) {
        Disposable disposable = requestStore.requestLogin(account.getMobile(), Md5Utils.md5(account.getPassword()), new Consumer<LoginRespond>() {
            @Override
            public void accept(LoginRespond data) {
                if (data != null && data.getCode() == 200) {
                    accountDAO.updateToken(account.getMobile(),data.getData());
                    requestUserInfo(data.getData());
                } else {
                    requestAllEnd();
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
                requestAllEnd();
            }
        });
        mCompositeSubscription.add(disposable);
    }

    /**
     * 请求用户数据
     *
     * @param token
     * @return
     */
    private void requestUserInfo(String token) {
        Disposable disposable = requestStore.requestUserInfo(token, new Consumer<UserInfoRespond>() {
            @Override
            public void accept(UserInfoRespond data) {
                userDAO.save(data.getData());
                requestAllEnd();
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) {
                if (NetWorkUtil.isNetConnected(context)) {
                    Logger.d("请求用户信息数据失败！！！---有网络");
                } else {
                    Logger.d("请求用户信息数据失败！！！---没网络");
                }
                requestAllEnd();
            }
        });
        mCompositeSubscription.add(disposable);
    }

    private void requestAllEnd() {
        requestNum++;
        if (requestNum == request_all_num) {
            mView.goMainActivity();
        }
    }
}
