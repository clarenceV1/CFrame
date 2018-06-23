package com.cai.work.ui.news;

import com.cai.framework.base.GodBasePresenter;
import com.cai.work.bean.News;
import com.cai.work.common.RequestStore;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class NewsPresenter extends GodBasePresenter<NewsView> {

    @Inject
    RequestStore requestStore;

    @Override
    public void onAttached() {

    }

    public void requestNews(int page) {
        Disposable disposable = requestStore.requestNews(page, new Consumer<List<News>>() {
            @Override
            public void accept(List<News> data) {
                mView.update(data);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) {
                mView.toast("请求异常");
            }
        });
        mCompositeSubscription.add(disposable);
    }
}
