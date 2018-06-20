package com.cai.work.ui.withdrawal;

import com.cai.framework.base.GodBaseView;
import com.cai.work.bean.Message;
import com.cai.work.bean.Withdrawal;
import com.cai.work.bean.WithdrawalDetail;

/**
 * Created by clarence on 2018/1/12.
 */

public interface WithdrawalDetailView extends GodBaseView {
    void refreshList(WithdrawalDetail data);
    void showToast(String message);
}
