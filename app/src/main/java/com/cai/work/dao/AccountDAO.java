package com.cai.work.dao;

import com.cai.work.bean.Account;

import javax.inject.Inject;

import io.objectbox.Box;
import io.objectbox.BoxStore;

public class AccountDAO {

    @Inject
    BoxStore boxStore;

    @Inject
    public AccountDAO() {
    }

    public void save(Account account) {
        Box<Account> box = boxStore.boxFor(Account.class);
        box.put(account);
    }

//    public Account getData() {
//        Box<Account> box = boxStore.boxFor(Account.class);
//    }
}
