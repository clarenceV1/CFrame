package com.cai.work.ui.fund;

import com.cai.framework.base.GodBasePresenter;
import com.cai.lib.logger.Logger;
import com.cai.work.bean.respond.FundDetailRespond;
import com.cai.work.common.DataStore;
import com.cai.work.common.RequestStore;
import com.example.clarence.utillibrary.NetWorkUtil;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class FundDetailPresenter extends GodBasePresenter<FundDetailView> {

    @Inject
    RequestStore requestStore;
    @Inject
    DataStore dataStore;

    @Inject
    public FundDetailPresenter() {
    }

    @Override
    public void onAttached() {

    }

    private void getData() {
        String token = dataStore.getToken();
        Disposable disposable = requestStore.requestFundDetail(1, token, new Consumer<FundDetailRespond>() {
            @Override
            public void accept(FundDetailRespond data) {

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
}
