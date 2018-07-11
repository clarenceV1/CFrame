package com.cai.work.ui.welfare;


import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.cai.framework.base.GodBasePresenter;
import com.cai.pullrefresh.BaseListPtrFrameLayout;
import com.cai.pullrefresh.PtrRecyclerView;
import com.cai.pullrefresh.lib.PtrFrameLayout;
import com.cai.work.R;
import com.cai.work.base.App;
import com.cai.work.base.AppBaseActivity;
import com.cai.work.bean.Welfare;
import com.cai.work.databinding.WelfareBinding;
import com.example.clarence.imageloaderlibrary.ILoadImage;
import com.example.clarence.utillibrary.ToastUtils;

import java.util.List;

import javax.inject.Inject;

@Route(path = "/MeetOne/WelfareActivity", name = "往期福利")
public class WelfareActivity extends AppBaseActivity<WelfareBinding> implements WelfareView {

    @Inject
    WelfarePresenter presenter;
    @Inject
    ILoadImage iLoadImage;
    @Autowired(name = "title")
    String title;

    PtrRecyclerView mPtrRecyclerView;
    WelfareAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ARouter.getInstance().inject(this);
        super.onCreate(savedInstanceState);
    }

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
        adapter = new WelfareAdapter(this, title, iLoadImage, presenter);
        mPtrRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mPtrRecyclerView.setAdapter(adapter);
        mViewBinding.pullListView.setCloseLoadMore(true);
        mViewBinding.pullListView.setOnPullLoadListener(new BaseListPtrFrameLayout.OnPullLoadListener() {
            @Override
            public void onRefresh(final PtrFrameLayout frame) {
                presenter.requestWelfare(false);
            }

            @Override
            public void onLoadMore() {
            }
        });
        presenter.requestWelfare(true);
    }

    @Override
    public int getLayoutId() {
        return R.layout.welfare;
    }

    @Override
    public void callBack(List<Welfare> welfareList) {
        mViewBinding.pullListView.refreshOrLoadMoreComplete(false);
        if (welfareList != null && welfareList.size() > 0) {
            adapter.setDatas(welfareList);
        }
    }

    @Override
    public void callBack(String message) {
        ToastUtils.showShort(message);
    }
}
