package com.cai.work.ui.news;

import com.cai.framework.base.GodBaseView;
import com.cai.work.bean.News;

import java.util.List;

public interface NewsView extends GodBaseView {
    void update(List<News> dataList);
    void toast(String msg);
}
