package com.cai.work.bean;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.annotation.Generated;
import io.objectbox.annotation.apihint.Internal;

/**
 * Created by clarence on 2018/1/12.
 */
@Entity
public class Account {
    @Id
    private long id;
    private String name;
    private String money;
    private String icon;

    @Generated(1538037900)
    @Internal
    /** This constructor was generated by ObjectBox and may change any time. */
    public Account(long id, String name, String money, String icon) {
        this.id = id;
        this.name = name;
        this.money = money;
        this.icon = icon;
    }

    @Generated(882125521)
    public Account() {
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
