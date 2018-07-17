package com.meetone.work.bean;

/**
 * Created by davy on 2018/3/5.
 */

public class Message extends BaseModel {
    /**
     * {
     * "id": 3,
     * "title": "测试消息",
     * "type": 2,
     * "url": "",
     * "create_dt": "2018-03-18 13:04:57",
     * "nickname": "more.one",
     * "avatar": "https:\/\/h5.weet.co\/images\/logo.png"
     * }
     */
    private int msg_id;
    private String title;
    private int type;
    private String url;
    private String create_dt;
    private String nickname;
    private String avatar;
    private int user_id;

    public int getMsg_id() {
        return msg_id;
    }

    public void setMsg_id(int msg_id) {
        this.msg_id = msg_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCreate_dt() {
        return create_dt;
    }

    public void setCreate_dt(String create_dt) {
        this.create_dt = create_dt;
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

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

}
