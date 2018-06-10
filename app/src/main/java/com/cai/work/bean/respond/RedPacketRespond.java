package com.cai.work.bean.respond;

import com.cai.work.bean.RedPacket;

public class RedPacketRespond extends BaseRespond {
    private RedPacket data;

    public RedPacket getData() {
        return data;
    }

    public void setData(RedPacket data) {
        this.data = data;
    }
}
