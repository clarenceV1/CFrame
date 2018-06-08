package com.cai.work.ui.main.fragment;

import android.view.View;

import com.cai.framework.base.GodBasePresenter;
import com.cai.work.base.AppBaseFragment;
import com.cai.work.R;
import com.cai.work.databinding.MainServiceFragmentBinding;

import java.util.List;

public class MainServiceFragment extends AppBaseFragment<MainServiceFragmentBinding> {

    @Override
    public int getLayoutId() {
        return R.layout.main_service_fragment;
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
