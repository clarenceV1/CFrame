package com.cai.work.ui.main.fragment;

import com.cai.framework.base.GodBaseView;
import com.cai.work.bean.IRecycleViewBaseData;

import java.util.List;

/**
 * Created by clarence on 2018/1/12.
 */

public interface MineView extends GodBaseView {
     void refreshData(List<IRecycleViewBaseData> dataList);

     void loginOut();
}
