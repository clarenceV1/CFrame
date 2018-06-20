package com.cai.work.ui.main.fragment;

import com.cai.framework.base.BaseLifecycleObserver;
import com.cai.framework.base.GodBasePresenter;
import com.cai.work.bean.Trade;
import com.cai.work.bean.respond.HomeRespond;
import com.cai.work.bean.respond.TradeRespond;
import com.cai.work.common.RequestStore;
import com.cai.work.dao.AccountDAO;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class MainTradePresenter extends GodBasePresenter<TradeView> {
    @Inject
    RequestStore requestStore;
    @Inject
    AccountDAO accountDAO;

    @Inject
    public MainTradePresenter() {
    }


    @Override
    public void onAttached() {
        data.put(BaseLifecycleObserver.CLASS_NAME, "mainHomeFragment=====>");
    }


    public void requestTradeData() {
        try {
            final String token = accountDAO.getToken();
            Disposable disposable = requestStore.requestTradeData(token, new Consumer<TradeRespond>() {
                @Override
                public void accept(TradeRespond data) {
                    if (data.getCode() == 200) {
                        Trade trade = data.getData();
                        if (trade != null) {
                            if (trade.getStock() != null) {
                                trade.setGp("A股");
                            }
                            if (trade.getGn_contract() != null) {
                                trade.setGn("国内期货");
                            }
                            if (trade.getGj_contract() != null) {
                                trade.setGj("国际期货");
                            }
                        }
                        mView.update(data.getData());
                    } else {
                        mView.toast(data.getResponseText());
                    }
                }
            }, new Consumer<Throwable>() {
                @Override
                public void accept(Throwable throwable) {
                    mView.toast("请求错误");
                }
            });
            mCompositeSubscription.add(disposable);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
