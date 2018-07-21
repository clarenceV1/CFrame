package com.cai.work.ui.main.fragment;

import com.cai.framework.base.GodBaseView;
import com.cai.work.bean.User;
import com.cai.work.bean.home.HomeItemData;

import java.util.List;

/**
 * Created by clarence on 2018/1/12.
 */

public interface HomeView extends GodBaseView {

    void reFreshView(List<HomeItemData> data);
    void requestError(String data);
    void reFreshTopView(User account);
}
