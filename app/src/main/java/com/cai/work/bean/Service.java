package com.cai.work.bean;

public class Service {
    //     "userName": "张三",
//             "avatarUrl": "",
//             "sign": "你走得太快，灵魂都跟不上了",
//             "qq": "12345678",
//             "telephone": "13888888888"
    private String userName;
    private String avatarUrl;
    private String sign;
    private String qq;
    private String telephone;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
}
