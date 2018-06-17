package com.cai.work.ui.invite;

import com.cai.framework.base.GodBasePresenter;
import com.cai.work.common.DataStore;
import com.cai.work.common.RequestStore;
import com.cai.work.dao.AccountDAO;
import com.cai.work.dao.UserDAO;

import javax.inject.Inject;

public class InviteWayPresenter extends GodBasePresenter<InviteWayView> {

    @Inject
    RequestStore requestStore;
    @Inject
    DataStore dataStore;
    @Inject
    UserDAO userDAO;
    @Inject
    AccountDAO accountDAO;

    @Inject
    public InviteWayPresenter() {
    }

    @Override
    public void onAttached() {

    }

}
