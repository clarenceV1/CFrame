package com.cai.work.dao;

import com.cai.work.bean.User;

import java.util.List;

import javax.inject.Inject;

import io.objectbox.Box;
import io.objectbox.BoxStore;
import io.objectbox.query.Query;

/**
 * Created by clarence on 2018/3/26.
 */

public class UserDAO {
    @Inject
    BoxStore boxStore;

    @Inject
    public UserDAO() {
    }

    public void addUser(User user) {
        Box<User> box = boxStore.boxFor(User.class);
        box.put(user);
    }

    public List<User> getUsers() {
        Box<User> box = boxStore.boxFor(User.class);
        Query<User> query = box.query().build();
        return query.find();
    }
}
