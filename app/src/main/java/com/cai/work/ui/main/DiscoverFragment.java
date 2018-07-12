package com.cai.work.ui.main;

import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.cai.pullrefresh.BaseListPtrFrameLayout;
import com.cai.pullrefresh.PtrRecyclerView;
import com.cai.pullrefresh.lib.PtrFrameLayout;
import com.cai.work.R;
import com.cai.work.base.App;
import com.cai.work.base.AppBaseFragment;
import com.cai.work.bean.Discover;
import com.cai.work.databinding.DiscoverBinding;
import com.example.clarence.imageloaderlibrary.ILoadImage;
import com.example.clarence.utillibrary.ToastUtils;

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

        presenter.requestDiscoverList(true);
    }

    @Override
    public void callBack(List<Discover> data) {
        mViewBinding.pullListView.refreshOrLoadMoreComplete(false);
        if (data != null && data.size() > 0) {
            adapter.setDatas(data);
        }
    }

    @Override
    public void callBack(String message) {
        ToastUtils.showShort(message);
    }
}
