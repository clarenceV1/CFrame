package com.cai.work.bean;

import com.cai.framework.bean.CBaseData;

public class MessageItem implements CBaseData{
//
//    data.data.id	id	number
//    data.data.content	内容	string
//    data.data.msgType	消息类型：1系统消息2官方消息3个人消息	number
//    data.data.readType	是否已读1已读2未读	number
//    data.data.createTime	创建日期

    private int id;
    private String content;
    private int msgType;
    private int readType;
    private String createTime;
    private boolean isShowMsgContent;//是否显示消息内容，只有用户点击小箭头才显示

    public boolean isShowMsgContent() {
        return isShowMsgContent;
    }

    public void setShowMsgContent(boolean showMsgContent) {
        isShowMsgContent = showMsgContent;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getMsgType() {
        return msgType;
    }

    public void setMsgType(int msgType) {
        this.msgType = msgType;
    }

    public int getReadType() {
        return readType;
    }

    public void setReadType(int readType) {
        this.readType = readType;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
