package com.cai.work.ui.rank;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.cai.framework.base.GodBasePresenter;
import com.cai.work.R;
import com.cai.work.base.AppBaseActivity;
import com.cai.work.dagger.component.DaggerAppComponent;
import com.cai.work.databinding.RankBinding;

import java.util.List;

import javax.inject.Inject;

@Route(path = "/AppModule/RankActivity", name = "排行榜")
public class RankActivity extends AppBaseActivity<RankBinding> implements RankView {
    @Inject
    RankPresenter presenter;

    @Override
    public void initDagger() {
        DaggerAppComponent.create().inject(this);
    }

    @Override
    public void addPresenters(List<GodBasePresenter> observerList) {
        observerList.add(presenter);
    }

    @Override
    public void initView() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.rank;
    }
}
