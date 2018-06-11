package com.cai.work.ui.main.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.callback.NavCallback;
import com.alibaba.android.arouter.launcher.ARouter;
import com.cai.framework.base.GodBasePresenter;
import com.cai.work.R;
import com.cai.work.base.AppBaseFragment;
import com.cai.work.bean.IRecycleViewBaseData;
import com.cai.work.bean.MineListData;
import com.cai.work.dagger.component.DaggerAppComponent;
import com.cai.work.databinding.MainMineFragmentBinding;
import com.cai.work.event.LoginStateEvent;
import com.example.clarence.imageloaderlibrary.ILoadImage;
import com.example.clarence.utillibrary.ToastUtils;

import org.greenrobot.eventbus.EventBus;

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
        mainMineAdapter = new MainMineAdapter(getContext(), imageLoader, presenter);
        mainMineAdapter.setItemClickListener(new MainMineAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int postion, IRecycleViewBaseData data) {
                if (data instanceof MineListData) {
                    MineListData mineListData = (MineListData) data;
                    ToastUtils.showShort(getActivity().getResources().getString(mineListData.getItemName()));
                    switch (postion) {
                        case 1:
                            ARouter.getInstance().build("/AppModule/FundDetailActivity").navigation();
                            break;
                        case 2:
                            ARouter.getInstance().build("/AppModule/BankCardListActivity").navigation();
                            break;
                        case 3:
                            ARouter.getInstance().build("/AppModule/MessageActivity").navigation();
                            break;
                        case 4:
                            ARouter.getInstance().build("/AppModule/RedPacketActivity").navigation();
                            break;
                        case 9:
                            ARouter.getInstance().build("/AppModule/SaveActivity").navigation();
                            break;
                    }
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

    @Override
    public void loginOut() {
        ARouter.getInstance().build("/AppModule/MainActivity").withInt("position", 1).navigation();
    }
}
