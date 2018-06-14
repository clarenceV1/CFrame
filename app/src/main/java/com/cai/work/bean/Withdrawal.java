package com.cai.work.bean;

import java.util.List;
import java.util.Map;

public class Withdrawal {
    private String balance;
    private Map<String, String> type;
    private Map<String, String> fee;
    private List<WithdrawalBank> bank_list;

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public Map<String, String> getType() {
        return type;
    }

    public void setType(Map<String, String> type) {
        this.type = type;
    }

    public Map<String, String> getFee() {
        return fee;
    }

    public void setFee(Map<String, String> fee) {
        this.fee = fee;
    }

    public List<WithdrawalBank> getBank_list() {
        return bank_list;
    }

    public void setBank_list(List<WithdrawalBank> bank_list) {
        this.bank_list = bank_list;
    }
}
