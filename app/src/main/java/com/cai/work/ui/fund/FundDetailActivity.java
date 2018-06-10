package com.cai.work.ui.fund;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.cai.framework.base.GodBasePresenter;
import com.cai.framework.pull.PullToRefreshBase;
import com.cai.work.R;
import com.cai.work.base.AppBaseActivity;
import com.cai.work.bean.FundDetail;
import com.cai.work.dagger.component.DaggerAppComponent;
import com.cai.work.databinding.FundDetailBinding;

import java.util.List;

import javax.inject.Inject;

@Route(path = "/AppModule/FundDetailActivity", name = "资金明细")
public class FundDetailActivity extends AppBaseActivity<FundDetailBinding> implements FundDetailView {

    @Inject
    FundDetailPresenter presenter;
    FundDetailAdapter adapter;
    int page = 1;
    int total = 0;//总数量
    int total_page = 2;//总页数
    ListView listView;

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
        mViewBinding.commonHeadView.tvTitle.setText(getString(R.string.fund_detail_title));
        listView = mViewBinding.pullListView.getRefreshableView();
        adapter = new FundDetailAdapter(this);
        listView.setAdapter(adapter);
        PullToRefreshBase.Mode.isShowFooterLoadingView = false;
        mViewBinding.pullListView.setMode(PullToRefreshBase.Mode.PULL_FROM_END);
        View emptyView = LayoutInflater.from(this).inflate(R.layout.empty_view,null);
        mViewBinding.pullListView.setEmptyView(emptyView);
        // Add an end-of-list listener
        mViewBinding.pullListView.setOnLastItemVisibleListener(new PullToRefreshBase.OnLastItemVisibleListener() {
            @Override
            public void onLastItemVisible() {
                if (page < total_page) {
                    ++page;
                    presenter.getData(page);
                }
            }
        });
        presenter.getData(page);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fund_detail;
    }

    @Override
    public void getData(FundDetail data) {
        mViewBinding.pullListView.onRefreshComplete();
        page = data.getCurrent();
        total = data.getTotal();
        total_page = data.getTotal_page();
        adapter.update(data.getData());

        if (page == total_page) {
            mViewBinding.pullListView.setMode(PullToRefreshBase.Mode.DISABLED);
            View view = LayoutInflater.from(this).inflate(R.layout.listview_foot, null);
            listView.addFooterView(view);
        }
    }
}
