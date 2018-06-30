package com.cai.work.ui.stock;

import com.cai.framework.base.GodBasePresenter;
import com.cai.work.bean.respond.StockBuyRespond;
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

    public void requestData(String code) {
        String token = accountDAO.getToken();
        Disposable disposable = requestStore.requestStockBuy(code, token, new Consumer<StockBuyRespond>() {
            @Override
            public void accept(StockBuyRespond data) {
                mView.callBack(data.getData());
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) {
                mView.callBack("错误");
            }
        });
        mCompositeSubscription.add(disposable);
    }
}
