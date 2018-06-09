package com.cai.work.ui.main.fragment;

import android.view.View;

import com.cai.framework.base.GodBasePresenter;
import com.cai.work.base.AppBaseFragment;
import com.cai.work.R;
import com.cai.work.databinding.MainTradeFragmentBinding;

import java.util.List;

/**
 * 交易
 */
public class MainTradeFragment extends AppBaseFragment<MainTradeFragmentBinding> {

    @Override
    public int getLayoutId() {
        return R.layout.main_trade_fragment;
    }

    @Override
    public void initView(View view) {

    }

    @Override
    public void addPresenters(List<GodBasePresenter> observerList) {

    }

    @Override
    public void initDagger() {

    }
}
