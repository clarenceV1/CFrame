package com.cai.work.dao;

import com.cai.work.bean.User;

import java.util.List;

import javax.inject.Inject;

import io.objectbox.Box;
import io.objectbox.BoxStore;

public class UserDAO {

    @Inject
    BoxStore boxStore;

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
        List<User> accounts = box.query().build().find();
        if (accounts != null && accounts.size() > 0) {
            return accounts.get(0);
        }
        return null;
    }

    public void deleteAll(){
        Box<User> box = boxStore.boxFor(User.class);
        box.removeAll();
    }
}
