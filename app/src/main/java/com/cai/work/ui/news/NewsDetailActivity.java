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
import com.cai.work.base.AppBaseActivity;
import com.cai.work.bean.News;
import com.cai.work.databinding.NewsDetailBinding;

import java.util.List;

@Route(path = "/AppModule/NewsDetailActivity", name = "站内公告详情页")
public class NewsDetailActivity extends AppBaseActivity<NewsDetailBinding> {

    @Autowired(name = "news")
    String newsJson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ARouter.getInstance().inject(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initDagger() {
    }

    @Override
    public void addPresenters(List<GodBasePresenter> observerList) {
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

        if (!TextUtils.isEmpty(newsJson)) {
            News news = JSON.parseObject(newsJson,News.class);
            mViewBinding.tvTitle.setText(news.getTitle());
            mViewBinding.tvDate.setText(news.getCreateDate());
            mViewBinding.tvChannelName.setText(news.getChannelName());
            mViewBinding.tvContent.setText(Html.fromHtml(news.getContentPC()));
        }
    }
}
