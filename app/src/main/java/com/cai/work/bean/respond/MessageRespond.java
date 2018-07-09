package com.cai.work.bean.respond;

import com.cai.work.bean.Message;

import java.util.List;

public class MessageRespond extends BaseRespond {
    private List<Message> data;

    public List<Message> getData() {
        return data;
    }

    public void setData(List<Message> data) {
        this.data = data;
    }
}
