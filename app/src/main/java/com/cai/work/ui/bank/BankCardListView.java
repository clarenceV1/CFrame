package com.cai.work.ui.bank;

import com.cai.framework.base.GodBaseView;
import com.cai.work.bean.BankCard;

import java.util.List;

/**
 * Created by clarence on 2018/1/12.
 */

public interface BankCardListView extends GodBaseView {
    void update(List<BankCard> dataList);
}
