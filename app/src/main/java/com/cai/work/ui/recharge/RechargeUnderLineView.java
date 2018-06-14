package com.cai.work.ui.recharge;

import com.cai.framework.base.GodBaseView;
import com.cai.work.bean.RechargeBank;

import java.util.List;

/**
 * Created by clarence on 2018/1/12.
 */

public interface RechargeUnderLineView extends GodBaseView {
    void updateListView(List<RechargeBank> dataList);
    void payState(String msg);
}
