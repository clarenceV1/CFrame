package com.cai.work.ui.asset;

import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.cai.framework.base.GodBasePresenter;
import com.cai.framework.baseview.LoadingView;
import com.cai.pullrefresh.BaseListPtrFrameLayout;
import com.cai.pullrefresh.PtrRecyclerView;
import com.cai.pullrefresh.lib.PtrFrameLayout;
import com.cai.work.R;
import com.cai.work.base.App;
import com.cai.work.base.AppBaseActivity;
import com.cai.work.bean.Asset;
import com.cai.work.databinding.AseetBinding;
import com.example.clarence.imageloaderlibrary.ILoadImage;
import com.example.clarence.utillibrary.ToastUtils;

import java.util.List;

import javax.inject.Inject;

@Route(path = "/MeetOne/AssetActivity", name = "资产")
public class AssetActivity extends AppBaseActivity<AseetBinding> implements AssetView {

    @Inject
    AssetPresenter presenter;
    @Inject
    ILoadImage iLoadImage;
    PtrRecyclerView mPtrRecyclerView;
    AssetAdapter adapter;
    int page = 1;

    @Override
    public void initDagger() {
        App.getAppComponent().inject(this);
    }

    @Override
    public void addPresenters(List<GodBasePresenter> observerList) {
        observerList.add(presenter);
    }

    @Override
    public void initView() {
        mViewBinding.titleBar.setLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mViewBinding.titleBar.hideTitle();

        mPtrRecyclerView = (PtrRecyclerView) mViewBinding.pullListView.getRecyclerView();
        adapter = new AssetAdapter(this, iLoadImage, presenter);
        mPtrRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mPtrRecyclerView.setAdapter(adapter);
        mViewBinding.pullListView.setCloseLoadMore(true);
        mViewBinding.pullListView.setOnPullLoadListener(new BaseListPtrFrameLayout.OnPullLoadListener() {
            @Override
            public void onRefresh(final PtrFrameLayout frame) {
                page = 1;
                presenter.requestAsset(false, page);
            }

            @Override
            public void onLoadMore() {
                page++;
                presenter.requestAsset(true, page);
            }
        });
        mViewBinding.loadView.setClickListener(new LoadingView.LoadViewClickListener() {
            @Override
            public void onLoadViewClick(int status) {
                presenter.requestAsset(false, page);
            }
        });
        mViewBinding.loadView.setStatus(LoadingView.STATUS_LOADING);
        presenter.requestAsset(true, page);
    }

    @Override
    public int getLayoutId() {
        return R.layout.aseet;
    }

    @Override
    public void callBack(String message) {
        ToastUtils.showShort(message);
    }

    @Override
    public void callBack(List<Asset> assetList) {
        mViewBinding.pullListView.refreshOrLoadMoreComplete(false);
        if (assetList != null && assetList.size() > 0) {
            if (page == 1) {
                adapter.setDatas(assetList);
            } else {
                adapter.addDatas(assetList);
            }
        }
        if (adapter.getDatas().isEmpty()) {
            mViewBinding.loadView.setStatus(LoadingView.STATUS_NODATA);
        } else {
            mViewBinding.loadView.setStatus(LoadingView.STATUS_HIDDEN);
        }
    }
}
