package com.meetone.work.ui.main;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.cai.framework.baseview.LoadingView;
import com.cai.pullrefresh.BaseListPtrFrameLayout;
import com.cai.pullrefresh.PtrRecyclerView;
import com.cai.pullrefresh.lib.PtrFrameLayout;
import com.meetone.work.R;
import com.meetone.work.databinding.CandyBinding;
import com.example.clarence.imageloaderlibrary.ILoadImage;
import com.example.clarence.utillibrary.ToastUtils;
import com.meetone.work.base.App;
import com.meetone.work.base.AppBaseFragment;
import com.meetone.work.bean.CandyList;
import com.meetone.work.event.LoginEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import javax.inject.Inject;

public class CandyFragment extends AppBaseFragment<CandyBinding> implements CandyView {
    @Inject
    CandyPresenter presenter;
    CandyAdapter adapter;

    @Inject
    ILoadImage iLoadImage;

    private PtrRecyclerView mPtrRecyclerView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

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

        mPtrRecyclerView = (PtrRecyclerView) mViewBinding.pullListView.getRecyclerView();
        adapter = new CandyAdapter(getContext(), iLoadImage, presenter);
        mPtrRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mPtrRecyclerView.setAdapter(adapter);
        mViewBinding.pullListView.setCloseLoadMore(true);
        mViewBinding.pullListView.setOnPullLoadListener(new BaseListPtrFrameLayout.OnPullLoadListener() {
            @Override
            public void onRefresh(final PtrFrameLayout frame) {
                presenter.requestCandyList(false);
            }

            @Override
            public void onLoadMore() {
            }
        });
        mViewBinding.loadView.setClickListener(new LoadingView.LoadViewClickListener() {
            @Override
            public void onLoadViewClick(int status) {
                presenter.requestCandyList(false);
            }
        });
        mViewBinding.loadView.setStatus(LoadingView.STATUS_LOADING);
        presenter.requestCandyList(true);
    }

    @Override
    public void callBack(String msg) {
        ToastUtils.showShort(msg);
    }

    @Override
    public void callBack(List<CandyList> data) {
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
    public void receiveCoinSuccess(int tokenId) {
        adapter.changData(tokenId);
        presenter.saveCache(adapter.getDatas());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLoginEvent(LoginEvent even) {
        mViewBinding.pullListView.autoRefresh();
    }
}
