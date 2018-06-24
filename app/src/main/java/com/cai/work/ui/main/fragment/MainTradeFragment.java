package com.cai.work.ui.main.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.ExpandableListView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.cai.framework.base.GodBasePresenter;
import com.cai.work.R;
import com.cai.work.base.AppBaseFragment;
import com.cai.work.bean.Trade;
import com.cai.work.dagger.component.DaggerAppComponent;
import com.cai.work.databinding.MainTradeFragmentBinding;
import com.example.clarence.utillibrary.ToastUtils;

import java.util.List;

import javax.inject.Inject;

/**
 * 交易
 */
public class MainTradeFragment extends AppBaseFragment<MainTradeFragmentBinding> implements TradeView {

    @Inject
    MainTradePresenter presenter;
    int selectedTabType = 1;
    Trade trade;
    TradeAdapter adapter;

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
        return R.layout.main_trade_fragment;
    }

    @Override
    public void initView(View view) {
        mViewBinding.commonHeadView.tvTitle.setText(getString(R.string.trade_title));
        mViewBinding.commonHeadView.ivGoBack.setVisibility(View.GONE);
        mViewBinding.tvTradeTabLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedTabType == 1) {
                    return;
                }
                selectedTabType = 1;
                switchTab();
            }
        });
        mViewBinding.tvTradeTabRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedTabType == 2) {
                    return;
                }
                selectedTabType = 2;
                switchTab();
            }
        });

        adapter = new TradeAdapter(getContext(), mViewBinding.expandListView);
        mViewBinding.expandListView.setAdapter(adapter);
        mViewBinding.expandListView.setHeaderView(LayoutInflater.from(getContext()).inflate(
                R.layout.trade_group_item, mViewBinding.expandListView, false));
        mViewBinding.expandListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                ARouter.getInstance().build("/AppModule/ForwardActivity").navigation();
                return true;
            }
        });
        switchTab();
        presenter.requestTradeData();
    }

    private void switchTab() {
        if (selectedTabType == 1) {
            mViewBinding.tvTradeTabLeft.setTextColor(getResources().getColor(R.color.home_forward_tab_color_selected));
            mViewBinding.tvTradeTabRight.setTextColor(getResources().getColor(R.color.home_forward_tab_color));
            if (trade != null) {
                trade.setGp("A股");
                adapter.notifyDataSetChanged();
            }
        } else {
            mViewBinding.tvTradeTabLeft.setTextColor(getResources().getColor(R.color.home_forward_tab_color));
            mViewBinding.tvTradeTabRight.setTextColor(getResources().getColor(R.color.home_forward_tab_color_selected));
            if (trade != null) {
                trade.setGp("股票模拟");
                adapter.notifyDataSetChanged();
            }
        }
        expandGroup();
    }

    @Override
    public void update(Trade trade) {
        this.trade = trade;
        adapter.update(trade);
        expandGroup();
    }

    public void expandGroup() {
        int groupCount = mViewBinding.expandListView.getCount();
        if (groupCount == 0) {
            return;
        }
        for (int i = 0; i < groupCount; i++) {
            mViewBinding.expandListView.expandGroup(i);
        }
    }

    @Override
    public void toast(String msg) {
        ToastUtils.showShort(msg);
    }
}
