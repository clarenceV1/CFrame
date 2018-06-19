package com.cai.work.ui.main.fragment;

import com.cai.framework.base.GodBaseView;
import com.cai.work.bean.IRecycleViewBaseData;
import com.cai.work.bean.Service;

import java.util.List;

/**
 * Created by clarence on 2018/1/12.
 */

public interface MainServiceView extends GodBaseView {
    void refreshData(List<Service> data);
    void toast(String msg);
}
