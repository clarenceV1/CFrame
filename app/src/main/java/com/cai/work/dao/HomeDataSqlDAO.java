package com.cai.work.dao;

import java.util.List;

import javax.inject.Inject;

import io.objectbox.Box;

public class HomeDataSqlDAO extends BaseDAO{

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

    public HomeDataSql getHomeData() {
        Box<HomeDataSql> box = boxStore.boxFor(HomeDataSql.class);
        List<HomeDataSql> homeDataSqls = box.query().build().find();
        if (homeDataSqls != null && homeDataSqls.size() > 0) {
            return homeDataSqls.get(0);
        }
        return null;
    }
}
