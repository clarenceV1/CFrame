package com.cai.work.bean;

import java.util.List;

public class RebateDetail {
    private List<RebateItem> oneLever;//一级返佣数据
    private List<RebateItem> twoLever;//二级返佣数据

    public List<RebateItem> getOneLever() {
        return oneLever;
    }

    public void setOneLever(List<RebateItem> oneLever) {
        this.oneLever = oneLever;
    }

    public List<RebateItem> getTwoLever() {
        return twoLever;
    }

    public void setTwoLever(List<RebateItem> twoLever) {
        this.twoLever = twoLever;
    }
}
