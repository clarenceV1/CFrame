package com.cai.work.bean;

/**
 * Created by davy on 2018/3/5.
 */

public class RecordModel extends BaseModel {
    /**
     * {
     * "last_id": 401,
     * "user_id": 10012,
     * "token_id": 100,
     * "candy_total": 50,
     * "type": 2,
     * "create_dt": "2018-03-18 13:45:15",
     * "token_symbol": "TOP"
     * }
     */
    private int last_id;
    private int user_id;
    private int token_id;
    private float candy_total;
    private int type;
    private String create_dt;
    private String token_symbol;

    public int getLast_id() {
        return last_id;
    }

    public void setLast_id(int last_id) {
        this.last_id = last_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getToken_id() {
        return token_id;
    }

    public void setToken_id(int token_id) {
        this.token_id = token_id;
    }

    public float getCandy_total() {
        return candy_total;
    }

    public void setCandy_total(float candy_total) {
        this.candy_total = candy_total;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getCreate_dt() {
        return create_dt;
    }

    public void setCreate_dt(String create_dt) {
        this.create_dt = create_dt;
    }

    public String getToken_symbol() {
        return token_symbol;
    }

    public void setToken_symbol(String token_symbol) {
        this.token_symbol = token_symbol;
    }
}
