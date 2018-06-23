package com.cai.work.ui.main.fragment;

import com.cai.framework.base.GodBaseView;
import com.cai.work.bean.ForwardAccount;
import com.cai.work.bean.SocketInfo;
import com.cai.work.bean.StockAccount;
import com.cai.work.bean.StockHold;
import com.cai.work.bean.User;
import com.cai.work.bean.home.HomeItemData;

import java.util.List;

/**
 * Created by clarence on 2018/1/12.
 */

public interface HoldView extends GodBaseView {
    void socketInfo(SocketInfo socketInfo);

    void toast(String msg, int type);

    void stockHold(List<StockHold> dataList);

    void forwardAccount(List<ForwardAccount> dataList);

    void stockAccount(List<StockAccount> dataList);
}
