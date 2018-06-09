package com.cai.work.ui.main.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.cai.framework.base.GodBasePresenter;
import com.cai.work.R;
import com.cai.work.base.AppBaseFragment;
import com.cai.work.bean.IRecycleViewBaseData;
import com.cai.work.bean.MineListData;
import com.cai.work.dagger.component.DaggerAppComponent;
import com.cai.work.databinding.MainMineFragmentBinding;
import com.example.clarence.imageloaderlibrary.ILoadImage;
import com.example.clarence.utillibrary.ToastUtils;

import java.util.List;

import javax.inject.Inject;

public class MainMineFragment extends AppBaseFragment<MainMineFragmentBinding> implements MineView {
    @Inject
    MainMinePresenter presenter;
    @Inject
    ILoadImage imageLoader;
    MainMineAdapter mainMineAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.main_mine_fragment;
    }

    @Override
    public void addPresenters(List<GodBasePresenter> observerList) {
        observerList.add(presenter);
    }

    @Override
    public void initDagger() {
        DaggerAppComponent.create().inject(this);
    }

    @Override
    public void initView(View view) {
        initRecycleView();
    }

    private void initRecycleView() {
        LinearLayoutManager layoutmanager = new LinearLayoutManager(getContext());
        mViewBinding.mRecyclerView.setLayoutManager(layoutmanager);
        mainMineAdapter = new MainMineAdapter(getContext(), imageLoader);
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
        presenter.getMineData();
    }

    @Override
    public void refreshData(List<IRecycleViewBaseData> dataList) {
        mainMineAdapter.updateData(dataList);
    }
}
