package com.cai.work.ui.fund;

import com.cai.framework.base.GodBasePresenter;
import com.cai.lib.logger.Logger;
import com.cai.work.bean.respond.FundDetailRespond;
import com.cai.work.common.DataStore;
import com.cai.work.common.RequestStore;
import com.cai.work.dao.AccountDAO;
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
    AccountDAO accountDAO;

    @Inject
    public FundDetailPresenter() {
    }

    @Override
    public void onAttached() {

    }

    public void getData(int page) {
        String token = accountDAO.getToken();
        Disposable disposable = requestStore.requestFundDetail(page, token, new Consumer<FundDetailRespond>() {
            @Override
            public void accept(FundDetailRespond data) {
                if (data != null && data.getCode() == 200) {
                    mView.getData(data.getData());
                }
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) {
                if (NetWorkUtil.isNetConnected(context)) {
                    Logger.d("请求资金明细数据失败！！！---有网络");
                } else {
                    Logger.d("请求资金明细数据失败！！！---没网络");
                }
            }
        });
        mCompositeSubscription.add(disposable);
    }
}
