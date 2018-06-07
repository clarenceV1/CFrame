package com.cai.work.ui.main.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.cai.framework.base.GodBaseFragment;
import com.cai.work.R;
import com.cai.work.bean.IRecycleViewBaseData;
import com.cai.work.bean.MineListData;
import com.cai.work.dagger.component.DaggerAppComponent;
import com.cai.work.databinding.MainMineFragmentBinding;
import com.example.clarence.imageloaderlibrary.ILoadImage;
import com.example.clarence.utillibrary.ToastUtils;

import javax.inject.Inject;

public class MainMineFragment extends GodBaseFragment<MainMineFragmentBinding> {
    @Inject
    MainMinePresenter presenter;
    @Inject
    ILoadImage imageLoader;

    @Override
    public int getLayoutId() {
        return R.layout.main_mine_fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DaggerAppComponent.create().inject(this);
    }

    @Override
    public void initView(View view) {
        initRecycleView();
    }

    private void initRecycleView() {
        LinearLayoutManager layoutmanager = new LinearLayoutManager(getContext());
        mViewBinding.mRecyclerView.setLayoutManager(layoutmanager);
        MainMineAdapter mainMineAdapter = new MainMineAdapter(getContext(),imageLoader, presenter.getDatas());
        mainMineAdapter.setItemClickListener(new MainMineAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int postion, IRecycleViewBaseData data) {
                if (data instanceof MineListData) {
                    MineListData mineListData = (MineListData) data;
                    ToastUtils.showShort(getActivity().getResources().getString(mineListData.getItemName()));
                }
            }
        });
        mViewBinding.mRecyclerView.setAdapter(mainMineAdapter);
    }

}
