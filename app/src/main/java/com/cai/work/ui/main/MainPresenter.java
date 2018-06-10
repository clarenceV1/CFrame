package com.cai.work.ui.main;

import com.cai.framework.base.GodBasePresenter;
import com.cai.work.bean.User;
import com.cai.work.common.DataStore;
import com.cai.work.common.RequestStore;
import com.cai.work.dagger.component.DaggerAppComponent;
import com.cai.work.dao.UserDAO;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by clarence on 2018/1/12.
 */
public class MainPresenter extends GodBasePresenter<MainView> {
    @Inject
    DataStore dataStore;
    @Inject
    RequestStore requestStore;
    @Inject
    UserDAO userDAO;

    @Inject
    public MainPresenter() {
    }

    @Override
    public void onAttached() {

    }

    public boolean isLogin() {
        User user = userDAO.getData();
        return user != null;
    }

}
