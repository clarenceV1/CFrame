package com.cai.work.ui.main;

import android.view.View;

import com.cai.work.R;
import com.cai.work.base.App;
import com.cai.work.base.AppBaseFragment;
import com.cai.work.databinding.MineBinding;

import java.util.List;

import javax.inject.Inject;

public class MineFragment extends AppBaseFragment<MineBinding> implements MineView {

    @Inject
    MinePresenter presenter;

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
        return R.layout.mine;
    }

    @Override
    public void initView(View view) {
        setDefultShow(false);
    }

    public void setDefultShow(boolean defultShow) {
        if (presenter.isLogin()) {

        } else {

        }
    }
}
