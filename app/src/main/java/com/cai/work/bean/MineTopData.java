package com.cai.work.bean;

import com.cai.work.ui.main.fragment.MainMineAdapter;

public class MineTopData implements IRecycleViewBaseData {
    private String headIcon;
    private String account;
    private String money;

    public String getHeadIcon() {
        return headIcon;
    }

    public void setHeadIcon(String headIcon) {
        this.headIcon = headIcon;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    @Override
    public int getLayoutType() {
        return MainMineAdapter.TOP_VIEW;
    }
}
