package com.meetone.work.bean;

/**
 * Created by davy on 2018/3/7.
 */

public class PhoneCode extends BaseModel {
    /**
     * {
     * "data": {
     * "code": "546115",
     * "timer": 120
     * },
     * "message": "",
     * "errorcode": 0
     * }
     */
    private String code;
    private int timer;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getTimer() {
        return timer;
    }

    public void setTimer(int timer) {
        this.timer = timer;
    }
}
