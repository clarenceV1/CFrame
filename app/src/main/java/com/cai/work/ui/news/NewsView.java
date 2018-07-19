package com.cai.work.ui.news;

import com.cai.framework.base.GodBaseView;
import com.cai.work.bean.News;
import com.cai.work.bean.NewsDetail;
import com.cai.work.bean.NewsList;

import java.util.List;

public interface NewsView extends GodBaseView {
    void update(NewsList dataList);
    void toast(String msg);

    void update(NewsDetail newsDetail);
}
