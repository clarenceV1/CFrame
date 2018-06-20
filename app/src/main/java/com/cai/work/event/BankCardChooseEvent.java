package com.cai.work.event;

public class BankCardChooseEvent {
    public WithdrawalBank bankCard;

    public BankCardChooseEvent(WithdrawalBank bankCard) {
        this.bankCard = bankCard;
    }
}
