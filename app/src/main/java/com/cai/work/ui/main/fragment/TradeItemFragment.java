package com.cai.work.ui.main.fragment;

import android.os.Bundle;
import android.view.View;

import com.cai.framework.base.GodBasePresenter;
import com.cai.work.R;
import com.cai.work.base.AppBaseFragment;
import com.cai.work.bean.Trade;
import com.cai.work.databinding.HomeForwardFragmentBinding;

import java.util.ArrayList;
import java.util.List;

public class TradeItemFragment extends AppBaseFragment<HomeForwardFragmentBinding> {

    String type;
    List<Trade> dataList;

    @Override
    public void addPresenters(List<GodBasePresenter> observerList) {

    }

    @Override
    public void initDagger() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.trade_item_fragment;
    }

    @Override
    public void initView(View view) {
        initData();
        initListView();
    }

    private void initListView() {
        TradeItemAdapter adapter = adapter = new TradeItemAdapter(getContext(), dataList);
        mViewBinding.forwardListView.setAdapter(adapter);
    }

    private void initData() {
        Bundle bundle = getArguments();
        type = bundle.getString("type");
        dataList = (List<Trade>) bundle.getSerializable("dataList");
    }
}
