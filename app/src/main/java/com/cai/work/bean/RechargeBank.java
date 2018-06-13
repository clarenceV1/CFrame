package com.cai.work.bean;

import com.cai.framework.bean.CBaseData;

public class RechargeBank implements CBaseData {
    //    data.id	int	会员id
//    data.offlineName	string	收款人
//    data.offlineAccount	string	收款人账号
//    data.offlineBranch	string	开户行
//    data.imageUrl	string	银行卡图片，PC端图片展示地址
//    data.mImageUrl	string	银行卡图片，手机端图片显示
//    data.mbgColor	string	银行卡图片，手机端展示背景色
    private int id;
    private String offlineName;
    private String offlineAccount;
    private String offlineBranch;
    private String imageUrl;
    private String mImageUrl;
    private String mbgColor;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOfflineName() {
        return offlineName;
    }

    public void setOfflineName(String offlineName) {
        this.offlineName = offlineName;
    }

    public String getOfflineAccount() {
        return offlineAccount;
    }

    public void setOfflineAccount(String offlineAccount) {
        this.offlineAccount = offlineAccount;
    }

    public String getOfflineBranch() {
        return offlineBranch;
    }

    public void setOfflineBranch(String offlineBranch) {
        this.offlineBranch = offlineBranch;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getmImageUrl() {
        return mImageUrl;
    }

    public void setmImageUrl(String mImageUrl) {
        this.mImageUrl = mImageUrl;
    }

    public String getMbgColor() {
        return mbgColor;
    }

    public void setMbgColor(String mbgColor) {
        this.mbgColor = mbgColor;
    }
}
