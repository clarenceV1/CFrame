package com.meetone.work.bean;

import java.util.List;

/**
 * Created by davy on 2018/3/7.
 */

public class MineModel extends BaseModel {
    /**
     * {
     * "data": {
     * "user": {
     * "nation_code": 86,
     * "phone": "13806086783",
     * "nickname": "13806086783",//昵称
     * "avatar": ""//头像
     * },
     * "invite": {  //6级裂变邀请人数 总数 = m1+m7
     * "id": 32,
     * "user_id": 25,
     * "m1": 7,
     * "m2": 0,
     * "m3": 0,
     * "m4": 0,
     * "m5": 0,
     * "m6": 0
     * },
     * "bonus": {
     * "id": 2,
     * "grand_total": 3199,//累计获取
     * "token_id": 1,
     * "token_symbol": "PIE"
     * },
     * "inviterule": "这是规则规则规则",//邀请规则
     * "inviteurl": "http://condypie.one/invite/34fec200ac9b"//邀请地址
     * },
     * "message": "",
     * "errorcode": 0
     * }
     */

    private User user;
    private MineInviteModel invite;
    private MineBonusModle bonus;
    private String inviterule;
    private String inviteurl;
    private String invitetitle;
    private List<Invite> inviteList;//自己用的
    private int inviteTotal;//邀请总人数,自己用的

    public int getInviteTotal() {
        return inviteTotal;
    }

    public void setInviteTotal(int inviteTotal) {
        this.inviteTotal = inviteTotal;
    }

    public List<Invite> getInviteList() {
        return inviteList;
    }

    public void setInviteList(List<Invite> inviteList) {
        this.inviteList = inviteList;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public MineInviteModel getInvite() {
        return invite;
    }

    public void setInvite(MineInviteModel invite) {
        this.invite = invite;
    }

    public MineBonusModle getBonus() {
        return bonus;
    }

    public void setBonus(MineBonusModle bonus) {
        this.bonus = bonus;
    }

    public String getInviterule() {
        return inviterule;
    }

    public void setInviterule(String inviterule) {
        this.inviterule = inviterule;
    }

    public String getInviteurl() {
        return inviteurl;
    }

    public void setInviteurl(String inviteurl) {
        this.inviteurl = inviteurl;
    }

    public String getInvitetitle() {
        return invitetitle;
    }

    public void setInvitetitle(String invitetitle) {
        this.invitetitle = invitetitle;
    }
}
