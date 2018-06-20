package com.cai.work.ui.main.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.cai.framework.base.GodBasePresenter;
import com.cai.work.base.AppBaseFragment;
import com.cai.work.R;
import com.cai.work.dagger.component.DaggerAppComponent;
import com.cai.work.databinding.MainTradeFragmentBinding;

import java.io.Serializable;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

/**
 * 交易
 */
public class MainTradeFragment extends AppBaseFragment<MainTradeFragmentBinding> {

    @Inject
    MainTradePresenter presenter;
    int selectedTabType = 1;
    Map<String, WeakReference<Fragment>> fragmentMap = new HashMap<>();

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
                if(selectedTabType == 1){
                    return;
                }
                selectedTabType = 1;
                switchTab();
            }
        });
        mViewBinding.tvTradeTabRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selectedTabType == 2){
                    return;
                }
                selectedTabType = 2;
                switchTab();
            }
        });
        switchTab();
    }

    private void switchTab() {
        if (selectedTabType == 1) {
            mViewBinding.tvTradeTabLeft.setTextColor(getResources().getColor(R.color.home_forward_tab_color_selected));
            mViewBinding.tvTradeTabRight.setTextColor(getResources().getColor(R.color.home_forward_tab_color));
        } else {

            mViewBinding.tvTradeTabLeft.setTextColor(getResources().getColor(R.color.home_forward_tab_color));
            mViewBinding.tvTradeTabRight.setTextColor(getResources().getColor(R.color.home_forward_tab_color_selected));
        }
        Fragment fragment;
        WeakReference<Fragment> weakReference = fragmentMap.get("Left");
        if (weakReference != null) {
            fragment = weakReference.get();
        } else {
            if (selectedTabType == 1) { //LEFT
                fragment = getForwardFragment("left", null);
                fragmentMap.put("left", new WeakReference<>(fragment));
            } else {//RIGHT
                fragment = getForwardFragment("right", null);
                fragmentMap.put("right", new WeakReference<>(fragment));
            }
        }
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.commit();
    }

    public <T> Fragment getForwardFragment(String type, List<T> data) {
        Bundle bundle = new Bundle();
        bundle.putString("type", type);
        bundle.putSerializable("dataList", (Serializable) data);
        return Fragment.instantiate(getContext(), TradeItemFragment.class.getName(), bundle);
    }
}
