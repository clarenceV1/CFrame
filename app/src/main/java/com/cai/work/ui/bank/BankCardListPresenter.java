package com.cai.work.ui.bank;

import com.cai.framework.base.GodBasePresenter;
import com.cai.lib.logger.Logger;
import com.cai.work.bean.respond.BankCardRespond;
import com.cai.work.common.DataStore;
import com.cai.work.common.RequestStore;
import com.example.clarence.utillibrary.NetWorkUtil;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class BankCardListPresenter extends GodBasePresenter<BankCardListView> {

    @Inject
    RequestStore requestStore;
    @Inject
    DataStore dataStore;

    @Inject
    public BankCardListPresenter() {
    }

    @Override
    public void onAttached() {

    }

    public void getData() {
        String token = dataStore.getToken();
        Disposable disposable = requestStore.requestBankList(token, new Consumer<BankCardRespond>() {
            @Override
            public void accept(BankCardRespond data) {
                if (data != null && data.getCode() == 200) {
                    mView.update(data.getData());
                }
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) {
                if (NetWorkUtil.isNetConnected(context)) {
                    Logger.d("请求银行列表数据失败！！！---有网络");
                } else {
                    Logger.d("请求银行列表数据失败！！！---没网络");
                }
            }
        });
        mCompositeSubscription.add(disposable);
    }
}
