package com.cai.work.ui.fund;

import com.cai.framework.base.GodBaseView;
import com.cai.work.bean.Account;
import com.cai.work.bean.FundDetail;
import com.cai.work.bean.home.HomeItemData;
import com.cai.work.bean.respond.FundDetailRespond;

/**
 * Created by clarence on 2018/1/12.
 */

public interface FundDetailView extends GodBaseView {
    void getData(FundDetail data);
}
