package com.cai.work.ui.main;

import android.view.Display;
import android.view.View;

import com.cai.work.R;
import com.cai.work.base.App;
import com.cai.work.base.AppBaseFragment;
import com.cai.work.databinding.DiscoverBinding;

import java.util.List;

import javax.inject.Inject;

public class DiscoverFragment extends AppBaseFragment<DiscoverBinding> implements DiscoverView {

    @Inject
    DiscoverPresenter presenter;

    @Override
    public void addPresenters(List observerList) {
        observerList.add(presenter);
    }

    @Override
    public void initDagger() {
        App.getAppComponent().inject(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.discover;
    }

    @Override
    public void initView(View view) {

    }
}
