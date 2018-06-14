package com.cai.work.ui.bank;

import com.cai.framework.base.GodBasePresenter;
import com.cai.lib.logger.Logger;
import com.cai.work.bean.respond.BaseRespond;
import com.cai.work.bean.respond.WithdrawalRespond;
import com.cai.work.common.DataStore;
import com.cai.work.common.RequestStore;
import com.cai.work.dao.AccountDAO;
import com.cai.work.dao.UserDAO;
import com.cai.work.ui.withdrawal.WithdrawalView;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class BankCardChoosePresenter extends GodBasePresenter<BankCardChoosePresenter> {

    @Inject
    RequestStore requestStore;
    @Inject
    AccountDAO accountDAO;

    @Inject
    public BankCardChoosePresenter() {
    }

    @Override
    public void onAttached() {

    }
}
