package com.cai.work.ui.forward;

import com.cai.framework.base.GodBasePresenter;
import com.cai.work.bean.respond.CommonRespond;
import com.cai.work.bean.respond.ForwardBuyRespond;
import com.cai.work.common.RequestStore;
import com.cai.work.dao.AccountDAO;

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

    public void requestKaiCang(String code,String amount,String bond,String zy,String zs,String redbagIds,String openWay) {
        String token = accountDAO.getToken();
        Disposable disposable = requestStore.requestKaiCang(token, code, amount, bond, zy, zs, redbagIds, openWay, new Consumer<CommonRespond>() {
                    @Override
                    public void accept(CommonRespond data) {
                        mView.toast(data.getResponseText());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) {
                        mView.toast("错误");
                    }
                });
        mCompositeSubscription.add(disposable);
    }
}
