package com.cai.work.ui.candy;

import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.cai.framework.base.GodBasePresenter;
import com.cai.work.R;
import com.cai.work.base.App;
import com.cai.work.base.AppBaseActivity;
import com.cai.work.databinding.CandyDetailBinding;

import java.util.List;

import javax.inject.Inject;

@Route(path = "/MeetOne/CandyDetailActivity", name = "糖果详情页")
public class CandyDetailActivity extends AppBaseActivity<CandyDetailBinding> implements CandyDetailView {
    @Inject
    CandyDetailPresenter presenter;
    @Autowired(name = "tokenId")
    int tokenId;

    @Override
    public void initDagger() {
        App.getAppComponent().inject(this);
    }

    @Override
    public void addPresenters(List<GodBasePresenter> observerList) {
        observerList.add(presenter);
    }

    @Override
    public void initView() {
        mViewBinding.tvTest.setText("token:"+tokenId);
    }

    @Override
    public int getLayoutId() {
        return R.layout.candy_detail;
    }
}
