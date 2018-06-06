package com.cai.work.ui.listview;

import android.widget.Adapter;
import android.widget.BaseAdapter;

import com.alibaba.android.arouter.launcher.ARouter;
import com.cai.framework.base.GodBasePresenter;
import com.cai.work.bean.ScrollData;
import com.cai.work.ui.adapter.ScrollAdapter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class ListViewPresenterForRTB extends GodBasePresenter<ListViewForRTB> {
    @Inject
    public ListViewPresenterForRTB() {
    }

    @Override
    public void onAttached() {

    }

    public void goMainActivity() {
        ARouter.getInstance().build("/AppModule/MainActivity").navigation();
    }

    public List<ScrollData> getDatas() {
        List data = new ArrayList<>();
        for (int i = 1; i < 80; i++) {
            ScrollData scrollData = new ScrollData();
            scrollData.setName("这是第" + i + "个数据");
            data.add(scrollData);
        }
        return data;
    }
}
