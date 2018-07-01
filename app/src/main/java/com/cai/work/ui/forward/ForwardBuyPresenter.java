package com.cai.work.ui.forward;

import com.cai.framework.base.GodBasePresenter;
import com.cai.work.bean.Forward;
import com.cai.work.bean.ForwardRecord;
import com.cai.work.bean.respond.ForwardBuyRespond;
import com.cai.work.bean.respond.ForwardContractsRespond;
import com.cai.work.common.RequestStore;
import com.cai.work.dao.AccountDAO;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class ForwardBuyPresenter extends GodBasePresenter<ForwardBuyView> {

    @Inject
    RequestStore requestStore;
    @Inject
    AccountDAO accountDAO;

    @Inject
    public ForwardBuyPresenter() {
    }

    @Override
    public void onAttached() {

    }

    public void requestForwardBuy(String code) {
        String token = accountDAO.getToken();
        Disposable disposable = requestStore.requestForwardBuy(token, code,new Consumer<ForwardBuyRespond>() {
            @Override
            public void accept(ForwardBuyRespond data) {
                mView.callBack(data.getData());
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) {
                mView.toast("请求异常");
            }
        });
        mCompositeSubscription.add(disposable);
    }

}
