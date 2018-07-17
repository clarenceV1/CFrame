package com.meetone.work.bean.respond;


import com.meetone.work.bean.Message;

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
