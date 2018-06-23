package com.cai.work.ui.news;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;

import com.cai.framework.base.GodBasePresenter;
import com.cai.framework.pull.PullToRefreshBase;
import com.cai.work.R;
import com.cai.work.base.AppBaseActivity;
import com.cai.work.bean.News;
import com.cai.work.dagger.component.DaggerAppComponent;
import com.cai.work.databinding.NewsBinding;

import java.util.List;

import javax.inject.Inject;

public class NewsActivity extends AppBaseActivity<NewsBinding> implements NewsView {

    @Inject
    NewsPresenter presenter;
    int page = 1;
    ListView listView;
    NewsAdapter adapter;
    boolean hasData;

    @Override
    public void initDagger() {
        DaggerAppComponent.create().inject(this);
    }

    @Override
    public void addPresenters(List<GodBasePresenter> observerList) {
        observerList.add(presenter);
    }

    @Override
    public int getLayoutId() {
        return R.layout.news;
    }

    @Override
    public void initView() {
        mViewBinding.commonHeadView.ivGoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mViewBinding.commonHeadView.tvTitle.setText(getString(R.string.news_title));
        listView = mViewBinding.pullListView.getRefreshableView();
        adapter = new NewsAdapter(this);
        listView.setAdapter(adapter);
        PullToRefreshBase.Mode.isShowFooterLoadingView = false;
        mViewBinding.pullListView.setMode(PullToRefreshBase.Mode.PULL_FROM_END);
        View emptyView = LayoutInflater.from(this).inflate(R.layout.empty_view, null);
        mViewBinding.pullListView.setEmptyView(emptyView);
        // Add an end-of-list listener
        mViewBinding.pullListView.setOnLastItemVisibleListener(new PullToRefreshBase.OnLastItemVisibleListener() {
            @Override
            public void onLastItemVisible() {
                if (hasData) {
                    ++page;
                    presenter.requestNews(page);
                }
            }
        });
        presenter.requestNews(page);
    }


    @Override
    public void update(List<News> dataList) {
        mViewBinding.pullListView.onRefreshComplete();
        adapter.update(dataList);

        if (dataList == null || dataList.size() == 0) {
            mViewBinding.pullListView.setMode(PullToRefreshBase.Mode.DISABLED);
            View view = LayoutInflater.from(this).inflate(R.layout.listview_foot, null);
            listView.addFooterView(view);
        }
    }

    @Override
    public void toast(String msg) {

    }
}
