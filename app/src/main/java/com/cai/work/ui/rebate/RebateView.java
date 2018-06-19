package com.cai.work.ui.rebate;

import com.cai.framework.base.GodBaseView;
import com.cai.work.bean.RebateDetail;

/**
 * Created by clarence on 2018/1/12.
 */

public interface RebateView extends GodBaseView {
    void update(RebateDetail rebateDetail);
    void toast(String msg);
}
