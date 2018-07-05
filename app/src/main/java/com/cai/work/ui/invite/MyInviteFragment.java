package com.cai.work.ui.invite;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.cai.framework.base.GodBasePresenter;
import com.cai.work.R;
import com.cai.work.base.AppBaseFragment;
import com.cai.work.bean.InviteOne;
import com.cai.work.databinding.HomeForwardFragmentBinding;
import com.cai.work.databinding.MyInviteBinding;
import com.cai.work.databinding.MyInviteFragmentBinding;

import java.util.List;

public class MyInviteFragment extends AppBaseFragment<MyInviteFragmentBinding> {

    String type;
    List<InviteOne> dataList;

    @Override
    public void addPresenters(List<GodBasePresenter> observerList) {

    }

    @Override
    public void initDagger() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.my_invite_fragment;
    }

    @Override
    public void initView(View view) {
        initData();
        initListView();
    }

    private void initListView() {
        if (dataList == null || dataList.size() == 0) {
            mViewBinding.listView.setVisibility(View.GONE);
            mViewBinding.llNoDataView.setVisibility(View.VISIBLE);
        } else {
            MyInviteAdapter adapter = new MyInviteAdapter(getContext(), dataList);
            mViewBinding.listView.setAdapter(adapter);
            mViewBinding.listView.setVisibility(View.VISIBLE);
            mViewBinding.llNoDataView.setVisibility(View.GONE);
        }
    }

    private void initData() {
        Bundle bundle = getArguments();
        type = bundle.getString("type");
        dataList = (List<InviteOne>) bundle.getSerializable("dataList");
    }
}
