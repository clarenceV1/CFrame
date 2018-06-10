package com.cai.work.ui.redPacket;

import com.cai.framework.base.GodBaseView;
import com.cai.work.bean.Message;
import com.cai.work.bean.RedPacket;

/**
 * Created by clarence on 2018/1/12.
 */

public interface RedPacketView extends GodBaseView {
    void refreshData(RedPacket redPacket);
    void showToast(String message);
}

