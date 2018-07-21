package com.cai.work.ui.main.fragment;

import android.view.View;
import android.widget.AdapterView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.alibaba.fastjson.JSON;
import com.cai.framework.base.GodBasePresenter;
import com.cai.framework.bean.CBaseData;
import com.cai.work.R;
import com.cai.work.base.App;
import com.cai.work.base.AppBaseFragment;
import com.cai.work.bean.Forward;
import com.cai.work.bean.home.HomeNphyData;
import com.cai.work.bean.home.HomeWphyData;
import com.cai.work.databinding.HomeForwardFragmentBinding;

import java.util.List;

import javax.inject.Inject;

public class HomeForwardFragment extends AppBaseFragment<HomeForwardFragmentBinding> {

    HomeForwardAdapter adapter;

    @Inject
    MainHomePresenter presenter;

    List dataList;

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
        return R.layout.home_forward_fragment;
    }

    @Override
    public void initView(View view) {
        initListView();
    }

    public void updateNp(List<HomeNphyData> nphyData) {
        if (nphyData != null) {
            if (adapter != null) {
                adapter.update(nphyData);
            } else {
                this.dataList = nphyData;
            }
        }
    }

    public void updateWp(List<HomeWphyData> wphyData) {
        if (wphyData != null) {
            if (adapter != null) {
                adapter.update(wphyData);
            } else {
                this.dataList = wphyData;
            }
        }
    }

    private void initListView() {
        adapter = new HomeForwardAdapter(getContext());
        mViewBinding.forwardListView.setAdapter(adapter);
        if (dataList != null) {
            adapter.update(dataList);
        }
        mViewBinding.forwardListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (!presenter.isLogin()) {
                    ARouter.getInstance().build("/AppModule/LoginActivity").navigation();
                    return;
                }
                Forward forward = null;
                CBaseData itemData = adapter.getItem(position);
                if (itemData instanceof HomeNphyData) {
                    HomeNphyData nphyData = (HomeNphyData) itemData;
                    forward = new Forward(nphyData.getContractName(), nphyData.getContractCode());
                } else {
                    HomeWphyData wphyData = (HomeWphyData) adapter.getItem(position);
                    forward = new Forward(wphyData.getContractName(), wphyData.getContractCode());
                }
                if (forward != null) {
                    ARouter.getInstance().build("/AppModule/ForwardActivity").withCharSequence("forwardJson", JSON.toJSONString(forward)).navigation();
                }
            }
        });
    }

}
