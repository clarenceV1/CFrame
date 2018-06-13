package com.cai.work.ui.recharge;

import com.cai.framework.base.GodBasePresenter;
import com.cai.work.common.DataStore;
import com.cai.work.common.RequestStore;
import com.cai.work.dao.AccountDAO;
import com.cai.work.dao.UserDAO;

import javax.inject.Inject;

public class RechargeUnderLinePresenter extends GodBasePresenter<RechargeView> {

    @Inject
    RequestStore requestStore;
    @Inject
    DataStore dataStore;
    @Inject
    UserDAO userDAO;
    @Inject
    AccountDAO accountDAO;

    @Inject
    public RechargeUnderLinePresenter() {
    }

    @Override
    public void onAttached() {

    }
}
