package com.cai.work.ui.bank;

import com.cai.framework.base.GodBaseView;
import com.cai.work.bean.Bank;
import com.cai.work.bean.Province;

import java.util.List;

/**
 * Created by clarence on 2018/1/12.
 */

public interface AddBankCardView extends GodBaseView {
    void refreshView(String msg);

    void getBankList(List<Bank> dataList,boolean showDialog);

    void showCityDialog(List<Province> provinces);
}
