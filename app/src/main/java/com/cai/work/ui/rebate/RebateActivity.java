package com.cai.work.ui.rebate;

import android.view.View;
import android.widget.AdapterView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.cai.framework.base.GodBasePresenter;
import com.cai.work.R;
import com.cai.work.base.App;
import com.cai.work.base.AppBaseActivity;
import com.cai.work.bean.RebateDetail;
import com.cai.work.bean.RebateItem;
import com.cai.work.dagger.component.DaggerAppComponent;
import com.cai.work.databinding.RebateBinding;
import com.example.clarence.utillibrary.ToastUtils;

import java.util.List;

import javax.inject.Inject;

@Route(path = "/AppModule/RebateActivity", name = "返佣明细")
public class RebateActivity extends AppBaseActivity<RebateBinding> implements RebateView {

    @Inject
    RebatePresenter presenter;
    int selectedTabType = 1;
    private List<RebateItem> oneLever;//一级返佣数据
    private List<RebateItem> twoLever;//二级返佣数据
    RebateAdapter adapter;

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
        mViewBinding.commonHeadView.ivGoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mViewBinding.commonHeadView.tvTitle.setText(getString(R.string.rebate_titile));
        mViewBinding.commonHeadView.tvRight.setText(getString(R.string.rebate_rule));
        mViewBinding.rlTab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedTabType == 1) {
                    return;
                }
                selectedTabType = 1;
                switchTab();
            }
        });
        mViewBinding.rlTab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedTabType == 2) {
                    return;
                }
                selectedTabType = 2;
                switchTab();
            }
        });
        mViewBinding.btnCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedTabType == 1) {
                    presenter.commit(oneLever);
                } else {
                    presenter.commit(twoLever);
                }
            }
        });

        adapter = new RebateAdapter(this);
        mViewBinding.listView.setAdapter(adapter);
        switchTab();
        presenter.requestData();
    }

    @Override
    public int getLayoutId() {
        return R.layout.rebate;
    }

    private void switchTab() {
        if (selectedTabType == 1) {
            mViewBinding.bottomLine1.setVisibility(View.VISIBLE);
            mViewBinding.bottomLine2.setVisibility(View.GONE);
            mViewBinding.tvTab1.setTextColor(getResources().getColor(R.color.ys_219_183_108));
            mViewBinding.tvTab2.setTextColor(getResources().getColor(R.color.ys_255_255_255));
        } else {
            mViewBinding.bottomLine1.setVisibility(View.GONE);
            mViewBinding.bottomLine2.setVisibility(View.VISIBLE);
            mViewBinding.tvTab1.setTextColor(getResources().getColor(R.color.ys_255_255_255));
            mViewBinding.tvTab2.setTextColor(getResources().getColor(R.color.ys_219_183_108));
        }
        if (selectedTabType == 1) {
            adapter.update(oneLever);
        } else {
            adapter.update(twoLever);
        }
    }

    @Override
    public void update(RebateDetail rebateDetail) {
        oneLever = rebateDetail.getOneLever();
        twoLever = rebateDetail.getTwoLever();
        switchTab();
    }

    @Override
    public void toast(String msg) {
        ToastUtils.showShort(msg);
    }
}
