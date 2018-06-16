package com.cai.work.dao;

import com.cai.work.bean.Account;
import com.cai.work.bean.Account_;

import javax.inject.Inject;

import io.objectbox.Box;

public class AccountDAO  extends  BaseDAO{

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

    public Account getAccount() {
        Box<Account> box = boxStore.boxFor(Account.class);
        return box.query().build().findFirst();
    }

    public void updateAll(Account newAccount) {
        Box<Account> box = boxStore.boxFor(Account.class);
        Account account = box.query().equal(Account_.mobile, newAccount.getMobile()).build().findFirst();
        account.setPassword(newAccount.getPassword());
        account.setToken(newAccount.getToken());
        box.put(account);
    }

    public void updatePassword(String mobile, String password) {
        Box<Account> box = boxStore.boxFor(Account.class);
        Account account = box.query().equal(Account_.mobile, mobile).build().findFirst();
        account.setPassword(password);
        box.put(account);
    }


    public void updateToken(String mobile, String token) {
        Box<Account> box = boxStore.boxFor(Account.class);
        Account account = box.query().equal(Account_.mobile, mobile).build().findFirst();
        account.setToken(token);
        box.put(account);
    }

    public String getToken() {
        Account account = getAccount();
        if (account != null) {
            return account.getToken();
        }
        return null;
    }
    public String getMobile(){
        Account account = getAccount();
        if (account != null) {
            return account.getMobile();
        }
        return null;
    }

    public void deleteAll() {
        Box<Account> box = boxStore.boxFor(Account.class);
        box.removeAll();
    }
}
