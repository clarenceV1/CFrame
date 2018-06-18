package com.cai.work.ui.withdrawal;

import com.cai.framework.base.GodBasePresenter;
import com.cai.lib.logger.Logger;
import com.cai.work.bean.respond.BaseRespond;
import com.cai.work.bean.respond.WithdrawalRespond;
import com.cai.work.common.DataStore;
import com.cai.work.common.RequestStore;
import com.cai.work.dao.AccountDAO;
import com.cai.work.dao.UserDAO;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class WithdrawalDetailPresenter extends GodBasePresenter<WithdrawalDetailView> {

    @Inject
    RequestStore requestStore;
    @Inject
    DataStore dataStore;
    @Inject
    UserDAO userDAO;
    @Inject
    AccountDAO accountDAO;

    @Inject
    public WithdrawalDetailPresenter() {
    }

    @Override
    public void onAttached() {

    }

    public void requestWithdrawal() {
        String token = accountDAO.getToken();
        Disposable disposable = requestStore.requestWithdrawal(token, new Consumer<WithdrawalRespond>() {
            @Override
            public void accept(WithdrawalRespond data) {
                Logger.d(data.getData());
//                    mView.update(data.getData());
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) {
                Logger.e(throwable.getMessage());
            }
        });
        mCompositeSubscription.add(disposable);
    }

    public void commitWithdrawal(int cardId,String amount,String password,int withdrawKind) {
        try {
            String token = accountDAO.getToken();
            Disposable disposable = requestStore.commitWithdrawal(cardId,amount,password,withdrawKind,token, new Consumer<BaseRespond>() {
                @Override
                public void accept(BaseRespond data) {
//                    if(data.getCode() == 200){
//                        mView.commitState("提交成功");
//                    }else{
//                        mView.commitState(data.getResponseText());
//                    }
                }
            }, new Consumer<Throwable>() {
                @Override
                public void accept(Throwable throwable) {
                    Logger.e(throwable.getMessage());
                }
            });
            mCompositeSubscription.add(disposable);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
