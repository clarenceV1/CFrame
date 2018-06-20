package com.cai.work.ui.withdrawal;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.cai.framework.base.GodBasePresenter;
import com.cai.framework.pull.PullToRefreshBase;
import com.cai.work.R;
import com.cai.work.base.AppBaseActivity;
import com.cai.work.bean.WithdrawalDetail;
import com.cai.work.bean.WithdrawalDetailItem;
import com.cai.work.dagger.component.DaggerAppComponent;
import com.cai.work.databinding.WithdrawalDetailBinding;

import java.util.List;

import javax.inject.Inject;

@Route(path = "/AppModule/WithdrawalDetailActivity", name = "提现明细")
public class WithdrawalDetailActivity extends AppBaseActivity<WithdrawalDetailBinding> implements WithdrawalDetailView {

    @Inject
    WithdrawalDetailPresenter presenter;
    int selectedTabType = 1;
    int page = 1;
    int total = 0;//总数量
    int total_page = 2;//总页数
    ListView listView;
    WithdrawalDetailAdapter adapter;
    List<WithdrawalDetailItem> data;

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
        mViewBinding.commonHeadView.ivGoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mViewBinding.commonHeadView.tvTitle.setText(getString(R.string.withdrawal_detail_titile));
        mViewBinding.commonHeadView.tvRight.setText(getString(R.string.withdrawal_detail_rule));
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
        mViewBinding.rlTab3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedTabType == 3) {
                    return;
                }
                selectedTabType = 3;
                switchTab();
            }
        });
        mViewBinding.rlTab4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedTabType == 4) {
                    return;
                }
                selectedTabType = 4;
                switchTab();
            }
        });
        listView = mViewBinding.pullListView.getRefreshableView();
        adapter = new WithdrawalDetailAdapter(this);
        listView.setAdapter(adapter);
        PullToRefreshBase.Mode.isShowFooterLoadingView = false;
        View emptyView = LayoutInflater.from(this).inflate(R.layout.empty_view, null);
        mViewBinding.pullListView.setEmptyView(emptyView);
        mViewBinding.pullListView.setMode(PullToRefreshBase.Mode.PULL_FROM_END);
        mViewBinding.pullListView.setOnLastItemVisibleListener(new PullToRefreshBase.OnLastItemVisibleListener() {
            @Override
            public void onLastItemVisible() {
                if (page < total_page) {
                    ++page;
                    presenter.requestData(page);
                }
            }
        });
        presenter.requestData(page);
    }

    private void switchTab() {
        switch (selectedTabType) {
            case 1:
                mViewBinding.bottomLine1.setVisibility(View.VISIBLE);
                mViewBinding.bottomLine2.setVisibility(View.GONE);
                mViewBinding.bottomLine3.setVisibility(View.GONE);
                mViewBinding.bottomLine4.setVisibility(View.GONE);
                mViewBinding.tvTab1.setTextColor(getResources().getColor(R.color.home_forward_tab_color_selected));
                mViewBinding.tvTab2.setTextColor(getResources().getColor(R.color.home_forward_tab_color));
                mViewBinding.tvTab3.setTextColor(getResources().getColor(R.color.home_forward_tab_color));
                mViewBinding.tvTab4.setTextColor(getResources().getColor(R.color.home_forward_tab_color));
                adapter.update(this.data);
                break;
            case 2:
                mViewBinding.bottomLine1.setVisibility(View.GONE);
                mViewBinding.bottomLine2.setVisibility(View.VISIBLE);
                mViewBinding.bottomLine3.setVisibility(View.GONE);
                mViewBinding.bottomLine4.setVisibility(View.GONE);
                mViewBinding.tvTab1.setTextColor(getResources().getColor(R.color.home_forward_tab_color));
                mViewBinding.tvTab2.setTextColor(getResources().getColor(R.color.home_forward_tab_color_selected));
                mViewBinding.tvTab3.setTextColor(getResources().getColor(R.color.home_forward_tab_color));
                mViewBinding.tvTab4.setTextColor(getResources().getColor(R.color.home_forward_tab_color));
                break;
            case 3:
                mViewBinding.bottomLine1.setVisibility(View.GONE);
                mViewBinding.bottomLine2.setVisibility(View.GONE);
                mViewBinding.bottomLine3.setVisibility(View.VISIBLE);
                mViewBinding.bottomLine4.setVisibility(View.GONE);
                mViewBinding.tvTab1.setTextColor(getResources().getColor(R.color.home_forward_tab_color));
                mViewBinding.tvTab2.setTextColor(getResources().getColor(R.color.home_forward_tab_color));
                mViewBinding.tvTab3.setTextColor(getResources().getColor(R.color.home_forward_tab_color_selected));
                mViewBinding.tvTab4.setTextColor(getResources().getColor(R.color.home_forward_tab_color));
                break;
            case 4:
                mViewBinding.bottomLine1.setVisibility(View.GONE);
                mViewBinding.bottomLine2.setVisibility(View.GONE);
                mViewBinding.bottomLine3.setVisibility(View.GONE);
                mViewBinding.bottomLine4.setVisibility(View.VISIBLE);
                mViewBinding.tvTab1.setTextColor(getResources().getColor(R.color.home_forward_tab_color));
                mViewBinding.tvTab2.setTextColor(getResources().getColor(R.color.home_forward_tab_color));
                mViewBinding.tvTab3.setTextColor(getResources().getColor(R.color.home_forward_tab_color));
                mViewBinding.tvTab4.setTextColor(getResources().getColor(R.color.home_forward_tab_color_selected));
                break;
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.withdrawal_detail;
    }

    @Override
    public void refreshList(WithdrawalDetail data) {
        mViewBinding.pullListView.onRefreshComplete();
        page = data.getCurrent();
        total = data.getTotal();
        total_page = data.getTotal_page();
        this.data = data.getData();
        adapter.update(this.data);

        if (page == total_page) {
            mViewBinding.pullListView.setMode(PullToRefreshBase.Mode.DISABLED);
            View view = LayoutInflater.from(this).inflate(R.layout.listview_foot, null);
            listView.addFooterView(view);
        }
    }

    @Override
    public void showToast(String message) {

    }
}
