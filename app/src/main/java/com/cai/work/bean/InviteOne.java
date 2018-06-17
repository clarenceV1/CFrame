package com.cai.work.bean;

import com.cai.framework.bean.CBaseData;

import java.io.Serializable;

public class InviteOne implements Serializable,CBaseData{
    /*			"id": 688,
                        "realName": "",
                        "userName": "130****1732",
                        "registerDate": "2018-06-12"*/
    private int id;
    private String realName;
    private String userName;
    private String registerDate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(String registerDate) {
        this.registerDate = registerDate;
    }
}
