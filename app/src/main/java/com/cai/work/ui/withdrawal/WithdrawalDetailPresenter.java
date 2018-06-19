package com.cai.work.ui.withdrawal;

import com.cai.framework.base.GodBasePresenter;
import com.cai.lib.logger.Logger;
import com.cai.work.bean.respond.RebateRespond;
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

    public void requestData() {
        String token = accountDAO.getToken();
        Disposable disposable = requestStore.requestRebate(token, new Consumer<RebateRespond>() {
            @Override
            public void accept(RebateRespond data) {
                Logger.d(data.getData());
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
