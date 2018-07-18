package com.meetone.work.ui.main;

import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.cai.framework.baseview.LoadingView;
import com.cai.framework.imageload.ILoadImage;
import com.cai.pullrefresh.BaseListPtrFrameLayout;
import com.cai.pullrefresh.PtrRecyclerView;
import com.cai.pullrefresh.lib.PtrFrameLayout;
import com.example.clarence.utillibrary.ToastUtils;
import com.meetone.work.R;
import com.meetone.work.base.App;
import com.meetone.work.base.AppBaseFragment;
import com.meetone.work.bean.Discover;
import com.meetone.work.databinding.DiscoverBinding;

import java.util.List;

import javax.inject.Inject;

public class DiscoverFragment extends AppBaseFragment<DiscoverBinding> implements DiscoverView {

    @Inject
    DiscoverPresenter presenter;
    @Inject
    ILoadImage iLoadImage;

    PtrRecyclerView mPtrRecyclerView;
    DiscoverAdapter adapter;

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
        return R.layout.discover;
    }

    @Override
    public void initView(View view) {

        mPtrRecyclerView = (PtrRecyclerView) mViewBinding.pullListView.getRecyclerView();
        adapter = new DiscoverAdapter(getContext(), iLoadImage,presenter);
        mPtrRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mPtrRecyclerView.setAdapter(adapter);
        mViewBinding.pullListView.setCloseLoadMore(true);
        mViewBinding.pullListView.setOnPullLoadListener(new BaseListPtrFrameLayout.OnPullLoadListener() {
            @Override
            public void onRefresh(final PtrFrameLayout frame) {
                presenter.requestDiscoverList(false);
            }

            @Override
            public void onLoadMore() {
            }
        });
        mViewBinding.loadView.setClickListener(new LoadingView.LoadViewClickListener() {
            @Override
            public void onLoadViewClick(int status) {
                presenter.requestDiscoverList(false);
            }
        });
        mViewBinding.loadView.setStatus(LoadingView.STATUS_LOADING);
        presenter.requestDiscoverList(true);
    }

    @Override
    public void callBack(List<Discover> data) {
        if (data != null && data.size() > 0) {
            adapter.setDatas(data);
        }
        mViewBinding.pullListView.refreshOrLoadMoreComplete(false);
        if (adapter.getDatas().isEmpty()) {
            mViewBinding.loadView.setStatus(LoadingView.STATUS_NODATA);
        } else {
            mViewBinding.loadView.setStatus(LoadingView.STATUS_HIDDEN);
        }
    }

    @Override
    public void callBack(String message) {
        ToastUtils.showShort(message);
    }
}
