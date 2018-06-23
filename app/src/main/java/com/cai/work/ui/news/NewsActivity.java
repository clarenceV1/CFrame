package com.cai.work.ui.news;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.alibaba.fastjson.JSON;
import com.cai.framework.base.GodBasePresenter;
import com.cai.framework.pull.PullToRefreshBase;
import com.cai.work.R;
import com.cai.work.base.AppBaseActivity;
import com.cai.work.bean.News;
import com.cai.work.dagger.component.DaggerAppComponent;
import com.cai.work.databinding.NewsBinding;

import java.util.List;

import javax.inject.Inject;

@Route(path = "/AppModule/NewsActivity", name = "站内公告")
public class NewsActivity extends AppBaseActivity<NewsBinding> implements NewsView {

    @Inject
    NewsPresenter presenter;
    int page = 1;
    ListView listView;
    NewsAdapter adapter;
    boolean hasData = true;

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
        mViewBinding.pullListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                News news = (News) adapter.getItem(position);
                ARouter.getInstance().build("/AppModule/NewsDetailActivity").withCharSequence("news", JSON.toJSONString(news)).navigation();
            }
        });
    }


    @Override
    public void update(List<News> dataList) {
        mViewBinding.pullListView.onRefreshComplete();
        adapter.update(dataList);

        if (dataList == null || dataList.size() == 0) {
            hasData = false;
            mViewBinding.pullListView.setMode(PullToRefreshBase.Mode.DISABLED);
            View view = LayoutInflater.from(this).inflate(R.layout.listview_foot, null);
            listView.addFooterView(view);
        }
    }

    @Override
    public void toast(String msg) {

    }
}
