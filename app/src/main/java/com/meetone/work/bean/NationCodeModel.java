package com.meetone.work.bean;

/**
 * Created by davy on 2018/3/8.
 */

public class NationCodeModel extends BaseModel {
    private String contry;
    private String code;
    private String tag;

    public String getContry() {
        return contry;
    }

    public void setContry(String contry) {
        this.contry = contry;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
