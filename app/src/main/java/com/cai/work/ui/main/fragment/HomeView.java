package com.cai.work.ui.main.fragment;

import com.cai.framework.base.GodBaseView;
import com.cai.work.bean.Account;
import com.cai.work.bean.home.HomeItemData;

/**
 * Created by clarence on 2018/1/12.
 */

public interface HomeView extends GodBaseView {

    void reFreshView(HomeItemData data);
    void requestError(String data);
    void reFreshTopView(Account account);
}
