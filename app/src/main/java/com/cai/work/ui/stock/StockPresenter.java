package com.cai.work.ui.stock;

import com.cai.framework.base.GodBasePresenter;
import com.cai.work.bean.Forward;
import com.cai.work.bean.ForwardRecord;
import com.cai.work.bean.respond.ForwardContractsRespond;
import com.cai.work.bean.respond.StockHQRespond;
import com.cai.work.bean.respond.StockListRespond;
import com.cai.work.bean.respond.StockTradeRespond;
import com.cai.work.common.RequestStore;
import com.cai.work.dao.AccountDAO;
import com.cai.work.ui.forward.ForwardView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class StockPresenter extends GodBasePresenter<StockView> {

    @Inject
    RequestStore requestStore;
    @Inject
    AccountDAO accountDAO;

    @Inject
    public StockPresenter() {
    }

    @Override
    public void onAttached() {

    }

    public void requestStockTrade() {
        String token = accountDAO.getToken();
        Disposable disposable = requestStore.requestStockTrade(token, new Consumer<StockTradeRespond>() {
            @Override
            public void accept(StockTradeRespond data) {
                mView.callBack(data.getData());
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) {
                mView.toast("请求异常", 3);
            }
        });
        mCompositeSubscription.add(disposable);
    }


    public void requestStockHq(String stock_code) {
        Disposable disposable = requestStore.requestStockHq(stock_code, new Consumer<StockHQRespond>() {
            @Override
            public void accept(StockHQRespond data) {
                mView.callBack(data.getData());
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) {
                mView.toast("请求异常", 3);
            }
        });
        mCompositeSubscription.add(disposable);
    }

    public void requestStockList(String search) {
        Disposable disposable = requestStore.requestStockList(search, new Consumer<StockListRespond>() {
            @Override
            public void accept(StockListRespond data) {
                mView.callBack(data.getData());
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) {
                mView.toast("请求异常", 3);
            }
        });
        mCompositeSubscription.add(disposable);
    }
}
