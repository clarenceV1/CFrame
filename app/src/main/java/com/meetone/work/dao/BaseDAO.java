package com.meetone.work.dao;

import com.meetone.work.base.App;

import io.objectbox.BoxStore;

public class BaseDAO {
    BoxStore boxStore;

    public BaseDAO() {
        this.boxStore = App.getBoxStore();
    }
}
