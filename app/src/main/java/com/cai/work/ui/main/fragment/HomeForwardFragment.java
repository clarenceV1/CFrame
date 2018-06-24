package com.cai.work.ui.main.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.cai.framework.base.GodBasePresenter;
import com.cai.work.R;
import com.cai.work.base.AppBaseFragment;
import com.cai.work.bean.home.HomeNphyData;
import com.cai.work.bean.home.HomeWphyData;
import com.cai.work.databinding.HomeForwardFragmentBinding;

import java.util.List;

public class HomeForwardFragment extends AppBaseFragment<HomeForwardFragmentBinding> {

    String type;
    List<HomeNphyData> nphyData;
    List<HomeWphyData> wphyData;

    @Override
    public void addPresenters(List<GodBasePresenter> observerList) {

    }

    @Override
    public void initDagger() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.home_forward_fragment;
    }

    @Override
    public void initView(View view) {
        initData();
        initListView();
    }

    private void initListView() {
        HomeForwardAdapter adapter = null;
        if ("left".equals(type)) {
            adapter = new HomeForwardAdapter(getContext(), nphyData);
        } else {
            adapter = new HomeForwardAdapter(getContext(), wphyData);
        }
        mViewBinding.forwardListView.setAdapter(adapter);
        mViewBinding.forwardListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ARouter.getInstance().build("/AppModule/ForwardActivity").navigation();
            }
        });
    }

    private void initData() {
        Bundle bundle = getArguments();
        type = bundle.getString("type");
        if ("left".equals(type)) {
            nphyData = (List<HomeNphyData>) bundle.getSerializable("dataList");
        } else {
            wphyData = (List<HomeWphyData>) bundle.getSerializable("dataList");
        }
    }
}
