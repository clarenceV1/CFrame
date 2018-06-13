package com.cai.work.ui.recharge;

import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.cai.framework.base.GodBasePresenter;
import com.cai.work.R;
import com.cai.work.base.AppBaseActivity;
import com.cai.work.bean.RechargeBank;
import com.cai.work.dagger.component.DaggerAppComponent;
import com.cai.work.databinding.RechargeUnderlineBinding;
import com.example.clarence.imageloaderlibrary.ILoadImage;

import java.util.List;

import javax.inject.Inject;

@Route(path = "/AppModule/RechargeUnderLine", name = "线下充值")
public class RechargeUnderLineActivity extends AppBaseActivity<RechargeUnderlineBinding> implements RechargeUnderLineView {
    @Inject
    ILoadImage imageLoader;
    @Inject
    RechargeUnderLinePresenter presenter;
    RechargeUnderLineAdapter adapter;

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
        mViewBinding.commonHeadView.tvTitle.setText(getString(R.string.save_titile));
        mViewBinding.commonHeadView.ivGoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        adapter = new RechargeUnderLineAdapter(this,imageLoader);
        mViewBinding.listView.setAdapter(adapter);
        presenter.requestRechargeBankList();
    }

    @Override
    public int getLayoutId() {
        return R.layout.recharge_underline;
    }

    @Override
    public void updateListView(List<RechargeBank> dataList) {
        adapter.update(dataList);
    }
}
