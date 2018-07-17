package com.meetone.work.base;

import com.cai.framework.base.GodBasePresenter;
import com.meetone.work.common.DataCacheStore;
import com.meetone.work.common.DataStore;
import com.meetone.work.common.FileStore;
import com.meetone.work.common.RequestStore;
import com.meetone.work.dao.UserDAO;

import javax.inject.Inject;

import dagger.Lazy;

public abstract class AppBasePresenter<V> extends GodBasePresenter<V> {

    @Inject
    public Lazy<DataStore> dataStore;
    @Inject
    public Lazy<RequestStore> requestStore;
    @Inject
    public Lazy<FileStore> fileStore;
    @Inject
    public Lazy<UserDAO> userDAO;
    @Inject
    public Lazy<DataCacheStore> cacheStore;

    @Inject
    public Lazy<StatisticsPresenter> statistics;

    public String getToken() {
        return userDAO.get().getToken();
    }

    public boolean isLogin() {
        return userDAO.get().isLogin();
    }

    public StatisticsPresenter getStatistics() {
        return statistics.get();
    }

    public long getUserId() {
        return userDAO.get().getUserId();
    }
}
