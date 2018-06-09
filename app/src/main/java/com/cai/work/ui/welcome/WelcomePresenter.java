package com.cai.work.ui.welcome;

import com.alibaba.fastjson.JSON;
import com.cai.framework.base.GodBasePresenter;
import com.cai.lib.logger.Logger;
import com.cai.work.R;
import com.cai.work.bean.HomeDataSql;
import com.cai.work.bean.home.HomeData;
import com.cai.work.common.DataStore;
import com.cai.work.common.RequestStore;
import com.cai.work.dagger.component.DaggerAppComponent;
import com.cai.work.dao.HomeDataSqlDAO;
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
        }
        Disposable disposable = requestStore.requestHomeData(new Consumer<HomeData>() {
            @Override
            public void accept(HomeData data) {
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
                mView.goMainActivity();
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) {
                if (NetWorkUtil.isNetConnected(context)) {
                    Logger.d("请求首页数据失败！！！---有网络");
                } else {
                    Logger.d("请求首页数据失败！！！---没网络");
                }
                mView.goMainActivity();
            }
        });
        mCompositeSubscription.add(disposable);
    }
}
