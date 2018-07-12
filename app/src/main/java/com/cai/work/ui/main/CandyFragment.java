package com.cai.work.ui.main;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.cai.pullrefresh.BaseListPtrFrameLayout;
import com.cai.pullrefresh.PtrRecyclerView;
import com.cai.pullrefresh.lib.PtrFrameLayout;
import com.cai.work.R;
import com.cai.work.base.App;
import com.cai.work.base.AppBaseFragment;
import com.cai.work.bean.CandyList;
import com.cai.work.databinding.CandyBinding;
import com.cai.work.event.LoginEvent;
import com.example.clarence.imageloaderlibrary.ILoadImage;
import com.example.clarence.utillibrary.ToastUtils;

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

        presenter.requestCandyList(true);
    }

    @Override
    public void callBack(String msg) {
        ToastUtils.showShort(msg);
    }

    @Override
    public void callBack(List<CandyList> data) {
        mViewBinding.pullListView.refreshOrLoadMoreComplete(false);
        if (data != null && data.size() > 0) {
            adapter.setDatas(data);
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
