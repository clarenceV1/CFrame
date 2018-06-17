package com.cai.work.bean;

import java.util.List;

public class Invite {
    //     "one_invite":[],
//             "two_invite":"",
//             "can_withdraw_money":255.95,
//             "already_withdraw_money":857.74,
//             "invited_url":"http://local.jtproject.com/zhuce?id=550&inviteType=member"
    private List<InviteOne> one_invite;
    private List<InviteOne> two_invite;
    private String can_withdraw_money;
    private String already_withdraw_money;
    private String invited_url;

    public String getCan_withdraw_money() {
        return can_withdraw_money;
    }

    public void setCan_withdraw_money(String can_withdraw_money) {
        this.can_withdraw_money = can_withdraw_money;
    }

    public String getAlready_withdraw_money() {
        return already_withdraw_money;
    }

    public void setAlready_withdraw_money(String already_withdraw_money) {
        this.already_withdraw_money = already_withdraw_money;
    }

    public String getInvited_url() {
        return invited_url;
    }

    public void setInvited_url(String invited_url) {
        this.invited_url = invited_url;
    }

    public List<InviteOne> getOne_invite() {
        return one_invite;
    }

    public void setOne_invite(List<InviteOne> one_invite) {
        this.one_invite = one_invite;
    }

    public List<InviteOne> getTwo_invite() {
        return two_invite;
    }

    public void setTwo_invite(List<InviteOne> two_invite) {
        this.two_invite = two_invite;
    }
}
