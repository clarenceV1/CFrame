package com.cai.work.bean;

import android.text.TextUtils;

import com.cai.framework.bean.CBaseData;
import com.example.clarence.utillibrary.StringUtils;

import java.io.Serializable;

public class WithdrawalBank implements CBaseData, Serializable {
//"id": 1845,
//        "cardNo": "24646647766686568",
//        "bankName": "建设银行",
//        "imageUrl": "\/\/www.hellceshi.com\/storage\/bank\/20180507\/7fa9ca5c0ade89995bb949a64dc16914.png",
//        "mImageUrl": "\/\/www.hellceshi.com\/storage\/bank\/20180522\/9983b240e625dc7e74428b024f71fc46.png",
//        "mbgColor": "#06569f"

    private int id;
    private String cardNo;
    private String bankName;
    private String imageUrl;
    private String mImageUrl;
    private String mbgColor;
    private boolean isChoose;//客户端自己加的

    public boolean isChoose() {
        return isChoose;
    }

    public void setChoose(boolean choose) {
        isChoose = choose;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
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

    public String getSimpleCardNo() {
        if (TextUtils.isEmpty(cardNo)) {
            return "";
        }
        return StringUtils.buildString("尾号",cardNo.substring(cardNo.length() - 4, cardNo.length()),"储蓄卡");
    }
}
