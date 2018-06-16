package com.cai.work.ui.withdrawal;

import com.cai.framework.base.GodBaseView;
import com.cai.work.bean.Withdrawal;

/**
 * Created by clarence on 2018/1/12.
 */

public interface WithdrawalPasswordView extends GodBaseView {
        void getMobile(String mobile);
        void toast(int type,String msg);
}
