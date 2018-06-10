package com.cai.work.ui.bank;

import android.view.LayoutInflater;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.cai.framework.base.GodBasePresenter;
import com.cai.work.R;
import com.cai.work.base.AppBaseActivity;
import com.cai.work.bean.Bank;
import com.cai.work.dagger.component.DaggerAppComponent;
import com.cai.work.databinding.BankListBinding;
import com.example.clarence.imageloaderlibrary.ILoadImage;

import java.util.List;

import javax.inject.Inject;

@Route(path = "/AppModule/BankListActivity", name = "银行列表")
public class BankListActivity extends AppBaseActivity<BankListBinding> implements BankListView {
    @Inject
    BankListPresenter presenter;
    BankListAdapter adapter;
    @Inject
    ILoadImage imageLoader;

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
        mViewBinding.commonHeadView.tvTitle.setText(getString(R.string.bank_title));
        mViewBinding.commonHeadView.ivGoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        adapter = new BankListAdapter(this, imageLoader);
        View footer = LayoutInflater.from(this).inflate(R.layout.bank_footer, null);
        mViewBinding.listView.addFooterView(footer);
        mViewBinding.listView.setAdapter(adapter);
        presenter.getData();
    }

    @Override
    public int getLayoutId() {
        return R.layout.bank_list;
    }

    @Override
    public void update(List<Bank> dataList) {
        adapter.update(dataList);
    }
}
