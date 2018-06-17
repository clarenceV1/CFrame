package com.cai.work.ui.invite;

import com.cai.framework.base.GodBasePresenter;
import com.cai.lib.logger.Logger;
import com.cai.work.bean.respond.InviteResond;
import com.cai.work.common.DataStore;
import com.cai.work.common.RequestStore;
import com.cai.work.dao.AccountDAO;
import com.cai.work.dao.UserDAO;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class InvitePresenter extends GodBasePresenter<InviteView> {

    @Inject
    RequestStore requestStore;
    @Inject
    DataStore dataStore;
    @Inject
    UserDAO userDAO;
    @Inject
    AccountDAO accountDAO;

    @Inject
    public InvitePresenter() {
    }

    @Override
    public void onAttached() {

    }


    public void requestInvite() {
        String token = accountDAO.getToken();
        Disposable disposable = requestStore.requestInvite(token, new Consumer<InviteResond>() {
            @Override
            public void accept(InviteResond data) {
                mView.refreshView(data.getData());
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
