package com.cai.work.bean;

import java.util.ArrayList;

/**
 * Created by davy on 2018/3/14.
 */

public class MineInviteModel extends BaseModel {
    /**
     * "invite": {  //6级裂变邀请人数 总数 = m1+m7
     * "id": 32,
     * "user_id": 25,
     * "m1": 7,
     * "m2": 0,
     * "m3": 0,
     * "m4": 0,
     * "m5": 0,
     * "m6": 0
     * }
     */
    private int m1;
    private int m2;
    private int m3;
    private int m4;
    private int m5;
    private int m6;

    public int getM1() {
        return m1;
    }

    public void setM1(int m1) {
        this.m1 = m1;
    }

    public int getM2() {
        return m2;
    }

    public void setM2(int m2) {
        this.m2 = m2;
    }

    public int getM3() {
        return m3;
    }

    public void setM3(int m3) {
        this.m3 = m3;
    }

    public int getM4() {
        return m4;
    }

    public void setM4(int m4) {
        this.m4 = m4;
    }

    public int getM5() {
        return m5;
    }

    public void setM5(int m5) {
        this.m5 = m5;
    }

    public int getM6() {
        return m6;
    }

    public void setM6(int m6) {
        this.m6 = m6;
    }

    public ArrayList<Integer> getDatas() {
        ArrayList<Integer> datas = new ArrayList<>();
        datas.add(m1);
        datas.add(m2);
        datas.add(m3);
        datas.add(m4);
        datas.add(m5);
        return datas;
    }
}
