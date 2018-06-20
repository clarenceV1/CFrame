package com.cai.work.ui.withdrawal;

import com.cai.framework.base.GodBasePresenter;
import com.cai.lib.logger.Logger;
import com.cai.work.bean.respond.RebateRespond;
import com.cai.work.bean.respond.WithdrawalDetailRespond;
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

    public void requestData(int page) {
        String token = accountDAO.getToken();
        Disposable disposable = requestStore.getRebateWithdraw(page,token, new Consumer<WithdrawalDetailRespond>() {
            @Override
            public void accept(WithdrawalDetailRespond data) {
                if (data != null && data.getCode() == 200) {
//                    addTestMsg(data.getData());
                    mView.refreshList(data.getData());
                }
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) {
                Logger.e(throwable.getMessage());
            }
        });
        mCompositeSubscription.add(disposable);
    }
}
