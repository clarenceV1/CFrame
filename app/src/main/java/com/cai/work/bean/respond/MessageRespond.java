package com.cai.work.bean.respond;

import com.cai.work.bean.Message;

public class MessageRespond extends BaseRespond{
    private Message data;

    public Message getData() {
        return data;
    }

    public void setData(Message data) {
        this.data = data;
    }
}
