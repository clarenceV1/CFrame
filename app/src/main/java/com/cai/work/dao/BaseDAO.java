package com.cai.work.dao;

import com.cai.work.base.App;

import io.objectbox.BoxStore;

public class BaseDAO {
    BoxStore boxStore;

    public BaseDAO() {
        this.boxStore = App.getBoxStore();
    }
}
