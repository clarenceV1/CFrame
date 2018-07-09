package com.cai.work.ui.message;

import com.cai.work.bean.Message;

import java.util.List;

public interface MessageView {
    void callBack(List<Message> messageList);

    void callBack(String message);
}
