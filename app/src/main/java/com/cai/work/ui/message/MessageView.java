package com.cai.work.ui.message;

import com.cai.framework.base.GodBaseView;
import com.cai.work.bean.Bank;
import com.cai.work.bean.Message;

import java.util.List;

/**
 * Created by clarence on 2018/1/12.
 */

public interface MessageView extends GodBaseView {
    void refreshMessageList(Message message);
    void showToast(String message);
}

