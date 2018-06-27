package com.cai.work.bean;

/**
 * Created by davy on 2018/3/14.
 */

public class MineBonusModle extends BaseModel{
    /**
     * bonus": {
     "id": 2,
     "grand_total": 3199,//累计获取
     "token_id": 1,
     "token_symbol": "PIE"
     }
     */
    private int grand_total;
    private String token_symbol;
    private String token_id;

    public int getGrand_total() {
        return grand_total;
    }

    public void setGrand_total(int grand_total) {
        this.grand_total = grand_total;
    }

    public String getToken_symbol() {
        return token_symbol;
    }

    public void setToken_symbol(String token_symbol) {
        this.token_symbol = token_symbol;
    }

    public String getToken_id() {
        return token_id;
    }

    public void setToken_id(String token_id) {
        this.token_id = token_id;
    }
}
