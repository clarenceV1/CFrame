package com.cai.work.ui.stock;

import com.cai.framework.base.GodBasePresenter;
import com.cai.work.bean.respond.StockHQRespond;
import com.cai.work.bean.respond.StockListRespond;
import com.cai.work.bean.respond.StockTradeRespond;
import com.cai.work.common.RequestStore;
import com.cai.work.dao.AccountDAO;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class StockBuyPresenter extends GodBasePresenter<StockBuyView> {

    @Inject
    RequestStore requestStore;
    @Inject
    AccountDAO accountDAO;

    @Inject
    public StockBuyPresenter() {
    }

    @Override
    public void onAttached() {

    }

    public void requestStockTrade() {
        String token = accountDAO.getToken();
        Disposable disposable = requestStore.requestStockTrade(token, new Consumer<StockTradeRespond>() {
            @Override
            public void accept(StockTradeRespond data) {
//                mView.callBack(data.getData());
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) {
//                mView.toast("请求异常", 3);
            }
        });
        mCompositeSubscription.add(disposable);
    }
}
