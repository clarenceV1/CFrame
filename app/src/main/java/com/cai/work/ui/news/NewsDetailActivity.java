package com.cai.work.ui.news;

import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.alibaba.fastjson.JSON;
import com.cai.framework.base.GodBasePresenter;
import com.cai.work.R;
import com.cai.work.base.App;
import com.cai.work.base.AppBaseActivity;
import com.cai.work.bean.News;
import com.cai.work.bean.NewsDetail;
import com.cai.work.bean.NewsList;
import com.cai.work.databinding.NewsDetailBinding;
import com.example.clarence.utillibrary.ToastUtils;

import java.util.List;

import javax.inject.Inject;

@Route(path = "/AppModule/NewsDetailActivity", name = "站内公告详情页")
public class NewsDetailActivity extends AppBaseActivity<NewsDetailBinding> implements NewsView {

    @Autowired(name = "news")
    int id;
    @Autowired(name = "channelName")
    String channelName;
    @Inject
    NewsPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ARouter.getInstance().inject(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initDagger() {
        App.getAppComponent().inject(this);
    }

    @Override
    public void addPresenters(List<GodBasePresenter> observerList) {
        observerList.add(presenter);
    }

    @Override
    public int getLayoutId() {
        return R.layout.news_detail;
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

        presenter.requestNewsDetial(id,channelName);
    }

    @Override
    public void update(NewsList dataList) {

    }

    @Override
    public void toast(String msg) {
        ToastUtils.showLong(msg);
    }

    @Override
    public void update(NewsDetail newsDetail) {
        if (newsDetail != null) {
            mViewBinding.tvTitle.setText(newsDetail.getTitle());
            mViewBinding.tvDate.setText(newsDetail.getCreateDate());
            mViewBinding.tvChannelName.setText(newsDetail.getChannelName());
            mViewBinding.tvContent.setText(Html.fromHtml(newsDetail.getContentPC()));
        }
    }
}
