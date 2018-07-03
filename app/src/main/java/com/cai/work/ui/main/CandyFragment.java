package com.cai.work.ui.main;

import android.view.View;
import android.widget.ListView;

import com.cai.framework.pull.PullToRefreshBase;
import com.cai.work.R;
import com.cai.work.base.App;
import com.cai.work.base.AppBaseFragment;
import com.cai.work.bean.CandyList;
import com.cai.work.databinding.CandyBinding;
import com.example.clarence.utillibrary.ToastUtils;

import java.util.List;

import javax.inject.Inject;

public class CandyFragment extends AppBaseFragment<CandyBinding> implements CandyView {
    @Inject
    CandyPresenter presenter;
    ListView listView;
    CandyAdapter adapter;

    @Override
    public void addPresenters(List observerList) {
        observerList.add(presenter);
    }

    @Override
    public void initDagger() {
        App.getAppComponent().inject(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.candy;
    }

    @Override
    public void initView(View view) {
        listView = mViewBinding.pullListView.getRefreshableView();
        mViewBinding.pullListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {

            }
        });
        adapter = new CandyAdapter(getContext());
        listView.setAdapter(adapter);
        presenter.requestCandyList();

    }

    @Override
    public void callBack(String msg) {
        ToastUtils.showShort(msg);
    }

    @Override
    public void callBack(List<CandyList> data) {
        adapter.updateAll(data);
    }
}
