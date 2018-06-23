package com.cai.work.bean;

import com.cai.framework.bean.CBaseData;

import java.io.Serializable;

public class News implements CBaseData, Serializable {
    //    "id": 1779,
//            "channelName": "Notice",
//            "title": "清明节交易安排",
//            "createDate": "2018-03-30",
//            "contentPC": "<p><img src=\"\/upload\/image\/20180330\/1522371203435106.png\" title=\"1522371203435106.png\" alt=\"QQ图片20180329085014.png\"\/><\/p>",
//            "imageUrl1": "",
//            "srcUrlM": null,
//            "srcUrlPC": null
    private int id;
    private String channelName;
    private String title;
    private String createDate;
    private String imageUrl1;
    private String srcUrlM;
    private String srcUrlPC;
    private String contentPC;

    public String getContentPC() {
        return contentPC;
    }

    public void setContentPC(String contentPC) {
        this.contentPC = contentPC;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getImageUrl1() {
        return imageUrl1;
    }

    public void setImageUrl1(String imageUrl1) {
        this.imageUrl1 = imageUrl1;
    }

    public String getSrcUrlM() {
        return srcUrlM;
    }

    public void setSrcUrlM(String srcUrlM) {
        this.srcUrlM = srcUrlM;
    }

    public String getSrcUrlPC() {
        return srcUrlPC;
    }

    public void setSrcUrlPC(String srcUrlPC) {
        this.srcUrlPC = srcUrlPC;
    }
}
