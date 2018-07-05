package com.cai.work.ui.bank;

import android.view.LayoutInflater;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.cai.framework.base.GodBasePresenter;
import com.cai.work.R;
import com.cai.work.base.App;
import com.cai.work.base.AppBaseActivity;
import com.cai.work.bean.BankCard;
import com.cai.work.dagger.component.DaggerAppComponent;
import com.cai.work.databinding.BankCardListBinding;
import com.example.clarence.imageloaderlibrary.ILoadImage;

import java.util.List;

import javax.inject.Inject;

@Route(path = "/AppModule/BankCardListActivity", name = "银行列表")
public class BankCardListActivity extends AppBaseActivity<BankCardListBinding> implements BankCardListView {
    @Inject
    BankCardListPresenter presenter;
    BankCardListAdapter adapter;
    @Inject
    ILoadImage imageLoader;

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
        mViewBinding.commonHeadView.tvTitle.setText(getString(R.string.bank_card_title));
        mViewBinding.commonHeadView.ivGoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        adapter = new BankCardListAdapter(this, imageLoader);
        View footer = LayoutInflater.from(this).inflate(R.layout.bank_footer, null);
        footer.findViewById(R.id.btnAddBank).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance().build("/AppModule/BankAddActivity").navigation();
            }
        });
        mViewBinding.listView.addFooterView(footer);
        mViewBinding.listView.setAdapter(adapter);
        mViewBinding.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance().build("/AppModule/BankAddActivity").navigation();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.getData();
    }

    @Override
    public int getLayoutId() {
        return R.layout.bank_card_list;
    }

    @Override
    public void update(List<BankCard> dataList) {
        if (adapter != null && dataList != null) {
            mViewBinding.loadView.setVisibility(View.GONE);
            adapter.update(dataList);
        } else {
            mViewBinding.loadView.setVisibility(View.VISIBLE);
        }
    }
}
