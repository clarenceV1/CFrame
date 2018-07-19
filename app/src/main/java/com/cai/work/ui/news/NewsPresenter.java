package com.cai.work.ui.news;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cai.framework.base.GodBasePresenter;
import com.cai.work.bean.NewsDetail;
import com.cai.work.bean.respond.CommonRespond;
import com.cai.work.bean.respond.NewsRespond;
import com.cai.work.common.RequestStore;


import java.io.IOException;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import okhttp3.ResponseBody;

public class NewsPresenter extends GodBasePresenter<NewsView> {

    @Inject
    RequestStore requestStore;

    @Inject
    public NewsPresenter() {
    }

    @Override
    public void onAttached() {

    }

    public void requestNews(int page) {
        Disposable disposable = requestStore.requestNews(page, new Consumer<NewsRespond>() {
            @Override
            public void accept(NewsRespond data) {
                mView.update(data.getData());
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) {
                mView.toast("请求异常");
            }
        });
        mCompositeSubscription.add(disposable);
    }

    public void requestNewsDetial(int id, final String channelName) {
        Disposable disposable = requestStore.requestNewsDetial(id, channelName, new Consumer<ResponseBody>() {
            @Override
            public void accept(ResponseBody respond) {
                try {
                    String res = respond.string();
                    CommonRespond commonRespond = JSON.parseObject(res, CommonRespond.class);
                    if (commonRespond.getCode() == 200) {
                        String data = commonRespond.getData();
                        if (!TextUtils.isEmpty(data)) {
                            JSONObject jsonObject = JSON.parseObject(data);
                            String curr = jsonObject.getString("curr");
                            if (!TextUtils.isEmpty(curr)) {
                                NewsDetail newsDetail = JSON.parseObject(curr, NewsDetail.class);
                                mView.update(newsDetail);
                            }
                        }
                    } else {
                        mView.toast(commonRespond.getResponseText());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
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
