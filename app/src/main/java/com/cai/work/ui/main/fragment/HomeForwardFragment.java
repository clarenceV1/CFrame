package com.cai.work.ui.main.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.alibaba.fastjson.JSON;
import com.cai.framework.base.GodBasePresenter;
import com.cai.work.R;
import com.cai.work.base.AppBaseFragment;
import com.cai.work.bean.Forward;
import com.cai.work.bean.home.HomeNphyData;
import com.cai.work.bean.home.HomeWphyData;
import com.cai.work.databinding.HomeForwardFragmentBinding;

import java.util.List;

public class HomeForwardFragment extends AppBaseFragment<HomeForwardFragmentBinding> {

    public static final String TYPE_NPHY = "type_nphy";
    public static final String TYPE_WPHY = "type_nphy";
    String type;
    List<HomeNphyData> nphyData;
    List<HomeWphyData> wphyData;
    HomeForwardAdapter adapter;

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
        if (TYPE_NPHY.equals(type)) {
            adapter = new HomeForwardAdapter(getContext(), nphyData);
        } else {
            adapter = new HomeForwardAdapter(getContext(), wphyData);
        }
        mViewBinding.forwardListView.setAdapter(adapter);
        mViewBinding.forwardListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Forward forward = null;
                if (TYPE_NPHY.equals(type)) {
                    HomeNphyData nphyData = (HomeNphyData) adapter.getItem(position);
                    forward = new Forward(nphyData.getContractName(), nphyData.getContractCode());
                } else {
                    HomeWphyData wphyData = (HomeWphyData) adapter.getItem(position);
                    forward = new Forward(wphyData.getContractName(), wphyData.getContractCode());
                }
                if (forward != null) {
                    ARouter.getInstance().build("/AppModule/ForwardActivity").withCharSequence("forwardJson", JSON.toJSONString(forward)).navigation();
                }
            }
        });
    }

    private void initData() {
        Bundle bundle = getArguments();
        type = bundle.getString("type");
        if (TYPE_NPHY.equals(type)) {
            nphyData = (List<HomeNphyData>) bundle.getSerializable("dataList");
        } else {
            wphyData = (List<HomeWphyData>) bundle.getSerializable("dataList");
        }
    }
}
