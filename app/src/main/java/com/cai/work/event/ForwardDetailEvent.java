package com.cai.work.event;

import com.cai.work.bean.ForwardDetail;

public class ForwardDetailEvent {
    public ForwardDetail detail;

    public ForwardDetailEvent(ForwardDetail detail) {
        this.detail = detail;
    }
}
