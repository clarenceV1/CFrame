package com.cai.work.ui.main;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;

import com.cai.framework.pull.PullToRefreshBase;
import com.cai.work.R;
import com.cai.work.base.App;
import com.cai.work.base.AppBaseFragment;
import com.cai.work.bean.CandyList;
import com.cai.work.databinding.CandyBinding;
import com.example.clarence.imageloaderlibrary.ILoadImage;
import com.example.clarence.utillibrary.ToastUtils;

import java.util.List;

import javax.inject.Inject;

public class CandyFragment extends AppBaseFragment<CandyBinding> implements CandyView {
    @Inject
    CandyPresenter presenter;
    ListView listView;
    CandyAdapter adapter;
    @Inject
    ILoadImage iLoadImage;

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
//        PullToRefreshBase.Mode.isShowFooterLoadingView = false;
//        mViewBinding.pullListView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
//        View emptyView = LayoutInflater.from(getContext()).inflate(R.layout.empty_view,null);
//        mViewBinding.pullListView.setEmptyView(emptyView);
//        listView = mViewBinding.pullListView.getRefreshableView();
//        mViewBinding.pullListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
//            @Override
//            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
//
//            }
//        });
        adapter = new CandyAdapter(getContext());
        mViewBinding.listView.setAdapter(adapter);
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
