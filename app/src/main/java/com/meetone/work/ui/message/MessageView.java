package com.meetone.work.ui.message;


import com.meetone.work.bean.Message;

import java.util.List;

public interface MessageView {
    void callBack(List<Message> messageList);

    void callBack(String message);
}
