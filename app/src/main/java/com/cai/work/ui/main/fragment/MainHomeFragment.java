package com.cai.work.ui.main.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.cai.framework.base.GodBaseFragment;
import com.cai.work.R;
import com.cai.work.bean.Account;
import com.cai.work.dagger.component.DaggerAppComponent;
import com.cai.work.databinding.MainHomeFragmentBinding;
import com.example.clarence.imageloaderlibrary.ILoadImage;
import com.example.clarence.imageloaderlibrary.ILoadImageParams;
import com.example.clarence.imageloaderlibrary.ImageForGlideParams;

import javax.inject.Inject;

public class MainHomeFragment extends GodBaseFragment<MainHomeFragmentBinding> {

    @Inject
    ILoadImage imageLoader;

    @Inject
    MainHomePresenter presenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DaggerAppComponent.create().inject(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.main_home_fragment;
    }

    @Override
    public void initView(View view) {
        initTopView();
        initRecycleView();
    }

    private void initRecycleView() {
//        LinearLayoutManager layoutmanager = new LinearLayoutManager(getContext());
//        mViewBinding.mRecyclerView.setLayoutManager(layoutmanager);
//        MainHomeAdapter adapter = new MainHomeAdapter(presenter.getDatas());
//        mViewBinding.mRecyclerView.setAdapter(adapter);
    }

    private void initTopView() {
        Account account = presenter.getAccountInfo();
        ILoadImageParams imageParams = new ImageForGlideParams.Builder().url(account.getIcon()).build();
        imageParams.setImageView(mViewBinding.ivIcon);
        imageLoader.loadImage(this, imageParams);

        mViewBinding.tvAccount.setText(account.getName());

    }
}
