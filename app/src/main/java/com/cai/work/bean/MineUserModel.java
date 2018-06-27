package com.cai.work.bean;

/**
 * Created by davy on 2018/3/14.
 */

public class MineUserModel extends BaseModel {
    /**
     * user": {
     * "user_id":123
     "nation_code": 86,
     "phone": "13806086783",
     "nickname": "13806086783",//昵称
     "avatar": ""//头像
     }
     */
    private long user_id;
    private int nation_code;
    private String phone;
    private String nickname;
    private String avatar;

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public int getNation_code() {
        return nation_code;
    }

    public void setNation_code(int nation_code) {
        this.nation_code = nation_code;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
