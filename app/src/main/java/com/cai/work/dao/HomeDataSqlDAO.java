package com.cai.work.dao;

import com.cai.work.bean.HomeDataSql;

import javax.inject.Inject;

import io.objectbox.Box;
import io.objectbox.BoxStore;

public class HomeDataSqlDAO {
    @Inject
    BoxStore boxStore;

    @Inject
    public HomeDataSqlDAO() {
    }

    /**
     * 保存首页数据
     *
     * @param homeDataSql
     */
    public void saveHomeData(HomeDataSql homeDataSql) {
        Box<HomeDataSql> box = boxStore.boxFor(HomeDataSql.class);
        box.removeAll();
        box.put(homeDataSql);
    }
}
