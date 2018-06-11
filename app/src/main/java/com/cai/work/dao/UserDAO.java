package com.cai.work.dao;

import com.cai.work.bean.User;

import javax.inject.Inject;

import io.objectbox.Box;

public class UserDAO extends BaseDAO {

    @Inject
    public UserDAO() {
    }

    public void save(User account) {
        Box<User> box = boxStore.boxFor(User.class);
        if (account != null) {
            box.removeAll();
        }
        box.put(account);
    }

    public User getData() {
        Box<User> box = boxStore.boxFor(User.class);
        return box.query().build().findFirst();
    }

    public void deleteAll() {
        Box<User> box = boxStore.boxFor(User.class);
        box.removeAll();
    }
}
