package com.cai.work.event;

import com.cai.work.bean.WithdrawalBank;

public class BankCardChooseEvent {
    public WithdrawalBank bankCard;

    public BankCardChooseEvent(WithdrawalBank bankCard) {
        this.bankCard = bankCard;
    }
}
