package com.cai.work.bean;

import com.cai.framework.bean.CBaseData;

public class Forward implements CBaseData {
    private String name;
    private String code;

    public Forward() {//不要删JSON 要用
    }

    public Forward(String name, String code) {
        this.name = name;
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
