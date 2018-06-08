package com.cai.work.bean.home;

/**
 * 公告
 */
public class HomeNoticeData{
//    data.notice.id	id	number
//    data.notice.channelName	公告--频道	string
//    data.notice.title	标题	string
//    data.notice.createDate	创建时间	number
//    data.notice.contentPC	内容	string
//    data.notice.imageUrl1	图片地址
    private int id;
    private String channelName;
    private String title;
    private String createDate;
    private String contentPC;
    private String imageUrl1;

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

    public String getContentPC() {
        return contentPC;
    }

    public void setContentPC(String contentPC) {
        this.contentPC = contentPC;
    }

    public String getImageUrl1() {
        return imageUrl1;
    }

    public void setImageUrl1(String imageUrl1) {
        this.imageUrl1 = imageUrl1;
    }
}
