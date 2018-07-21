package com.cai.work.ui.main.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.cai.framework.base.GodBasePresenter;
import com.cai.lib.logger.Logger;
import com.cai.pullrefresh.BaseListPtrFrameLayout;
import com.cai.pullrefresh.PtrRecyclerView;
import com.cai.pullrefresh.lib.PtrFrameLayout;
import com.cai.work.R;
import com.cai.work.base.App;
import com.cai.work.base.AppBaseFragment;
import com.cai.work.bean.User;
import com.cai.work.bean.home.HomeItemData;
import com.cai.work.dagger.component.DaggerAppComponent;
import com.cai.work.databinding.MainHomeFragmentBinding;
import com.example.clarence.imageloaderlibrary.GlideCircleTransform;
import com.example.clarence.imageloaderlibrary.ILoadImage;
import com.example.clarence.imageloaderlibrary.ILoadImageParams;
import com.example.clarence.imageloaderlibrary.ImageForGlideParams;
import com.example.clarence.utillibrary.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class MainHomeFragment extends AppBaseFragment<MainHomeFragmentBinding> implements HomeView {

    @Inject
    ILoadImage imageLoader;

    @Inject
    MainHomePresenter presenter;
    MainHomeAdapter adapter;
    private PtrRecyclerView mPtrRecyclerView;

    @Override
    public void addPresenters(List<GodBasePresenter> observerList) {
        observerList.add(presenter);
    }

    @Override
    public void initDagger() {
        App.getAppComponent().inject(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.main_home_fragment;
    }

    @Override
    public void initView(View view) {
        initRecycleView();
        mViewBinding.llUserInfo.setClickable(false);
        mViewBinding.llUserInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance().build("/AppModule/LoginActivity").navigation();
            }
        });
        mViewBinding.reload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.requestHomeData();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.getAccountInfo();
        presenter.requestHomeData();
    }

    private void initRecycleView() {
        mPtrRecyclerView = (PtrRecyclerView) mViewBinding.mRecyclerView.getRecyclerView();

        adapter = new MainHomeAdapter(mContext, imageLoader, getChildFragmentManager(), presenter);
        mPtrRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mPtrRecyclerView.setAdapter(adapter);
        mViewBinding.mRecyclerView.setCloseLoadMore(true);
        mViewBinding.mRecyclerView.setOnPullLoadListener(new BaseListPtrFrameLayout.OnPullLoadListener() {
            @Override
            public void onRefresh(final PtrFrameLayout frame) {
                presenter.requestHomeData();
            }

            @Override
            public void onLoadMore() {
            }
        });
    }


    @Override
    public void reFreshView(List<HomeItemData> data) {
        mViewBinding.mRecyclerView.refreshOrLoadMoreComplete(false);
        if (data != null && data.size() > 0) {
            mViewBinding.loadView.setVisibility(View.GONE);
            adapter.setDatas(data);
        }
    }

    @Override
    public void requestError(String data) {
        ToastUtils.showLong(data);
    }

    @Override
    public void reFreshTopView(User user) {
        refreshTopView(user);
    }

    private void refreshTopView(User user) {
        if (user != null) {
            ILoadImageParams imageParams = new ImageForGlideParams.Builder()
                    .url("http://" + user.getAvatarUrl())
                    .transformation(new GlideCircleTransform(getContext()))
                    .build();
            imageParams.setImageView(mViewBinding.ivIcon);
            imageLoader.loadImage(this, imageParams);

            mViewBinding.llUserInfo.setClickable(false);
            mViewBinding.tvAccount.setText(user.getMobile());
            mViewBinding.ivIcon.setVisibility(View.VISIBLE);
            mViewBinding.ivHeadOri.setVisibility(View.GONE);
        } else {
            mViewBinding.tvAccount.setText(getResources().getString(R.string.login));
            mViewBinding.llUserInfo.setClickable(true);
            mViewBinding.ivIcon.setVisibility(View.GONE);
            mViewBinding.ivHeadOri.setVisibility(View.VISIBLE);
        }
    }
}
