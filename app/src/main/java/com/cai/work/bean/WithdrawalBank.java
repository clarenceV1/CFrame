package com.cai.work.bean;

public class WithdrawalBank {
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
}
