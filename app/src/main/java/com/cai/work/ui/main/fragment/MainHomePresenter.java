package com.cai.work.ui.main.fragment;

import com.cai.work.bean.Account;
import com.cai.work.bean.IRecycleViewBaseData;

import java.util.List;

import javax.inject.Inject;

public class MainHomePresenter {

    @Inject
    public MainHomePresenter() {
    }

    public List<IRecycleViewBaseData> getDatas() {
        return null;
    }

    public Account getAccountInfo() {
        Account account = new Account();
        account.setMoney("1000000.00");
        account.setName("1377****6287");
        account.setIcon("http://img5.imgtn.bdimg.com/it/u=269889177,603310778&fm=27&gp=0.jpg");
        return account;
    }
}
