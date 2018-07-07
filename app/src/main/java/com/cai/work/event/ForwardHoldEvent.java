package com.cai.work.event;

import com.cai.work.bean.ForwardHold;

import java.util.List;

public class ForwardHoldEvent {
    public List<ForwardHold> data;

    public ForwardHoldEvent(List<ForwardHold> data) {
        this.data = data;
    }
}
