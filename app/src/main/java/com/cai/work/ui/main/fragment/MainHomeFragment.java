package com.cai.work.ui.main.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.cai.framework.base.GodBasePresenter;
import com.cai.lib.logger.Logger;
import com.cai.work.R;
import com.cai.work.base.AppBaseFragment;
import com.cai.work.bean.User;
import com.cai.work.bean.home.HomeItemData;
import com.cai.work.dagger.component.DaggerAppComponent;
import com.cai.work.databinding.MainHomeFragmentBinding;
import com.example.clarence.imageloaderlibrary.ILoadImage;
import com.example.clarence.imageloaderlibrary.ILoadImageParams;
import com.example.clarence.imageloaderlibrary.ImageForGlideParams;

import java.util.List;

import javax.inject.Inject;

public class MainHomeFragment extends AppBaseFragment<MainHomeFragmentBinding> implements HomeView {

    @Inject
    ILoadImage imageLoader;

    @Inject
    MainHomePresenter presenter;

    @Override
    public void addPresenters(List<GodBasePresenter> observerList) {
        observerList.add(presenter);
    }

    @Override
    public void initDagger() {
        DaggerAppComponent.create().inject(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.main_home_fragment;
    }

    @Override
    public void initView(View view) {
        initRecycleView();
        presenter.getAccountInfo();
        presenter.requestData();
        mViewBinding.llUserInfo.setClickable(false);
        mViewBinding.llUserInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance().build("/AppModule/LoginActivity").navigation();
            }
        });
    }

    private void initRecycleView() {
        LinearLayoutManager layoutmanager = new LinearLayoutManager(getContext());
        mViewBinding.mRecyclerView.setLayoutManager(layoutmanager);
    }


    @Override
    public void reFreshView(HomeItemData data) {
        MainHomeAdapter adapter = new MainHomeAdapter(mContext, imageLoader, data, getChildFragmentManager());
        mViewBinding.mRecyclerView.setAdapter(adapter);
    }

    @Override
    public void requestError(String data) {
        Logger.d(data);
    }

    @Override
    public void reFreshTopView(User user) {
        refreshTopView(user);
    }

    private void refreshTopView(User user) {
        if (user != null) {
            ILoadImageParams imageParams = new ImageForGlideParams.Builder().url(user.getAvatarUrl()).build();
            imageParams.setImageView(mViewBinding.ivIcon);
            imageLoader.loadImage(this, imageParams);

            mViewBinding.llUserInfo.setClickable(false);
            mViewBinding.tvAccount.setText(user.getMobile());
        } else {
            mViewBinding.tvAccount.setText(getResources().getString(R.string.login));
            mViewBinding.llUserInfo.setClickable(true);
            mViewBinding.ivIcon.setImageResource(R.drawable.ic_launcher);
        }
    }
}
