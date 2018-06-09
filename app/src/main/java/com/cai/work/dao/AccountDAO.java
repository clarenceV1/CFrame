package com.cai.work.dao;

import com.cai.work.bean.Account;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.objectbox.Box;
import io.objectbox.BoxStore;

@Singleton
public class AccountDAO {

    @Inject
    BoxStore boxStore;

    @Inject
    public AccountDAO() {
    }

    public void save(Account account) {
        Box<Account> box = boxStore.boxFor(Account.class);
        if (account != null) {
            box.removeAll();
        }
        box.put(account);
    }

    public Account getData() {
        Box<Account> box = boxStore.boxFor(Account.class);
        List<Account> accounts = box.query().build().find();
        if (accounts != null && accounts.size() > 0) {
            return accounts.get(0);
        }
        return null;
    }
}
