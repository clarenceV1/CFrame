package com.cai.work.base;

import com.cai.framework.base.GodBasePresenter;
import com.cai.work.common.DataCacheStore;
import com.cai.work.common.DataStore;
import com.cai.work.common.FileStore;
import com.cai.work.common.RequestStore;
import com.cai.work.dao.UserDAO;

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

    public boolean isLogin(){
        return userDAO.get().isLogin();
    }

    public StatisticsPresenter getStatistics() {
        return statistics.get();
    }
}
