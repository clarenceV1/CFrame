package com.cai.work.ui.main;

import com.cai.work.base.AppBasePresenter;
import com.cai.work.bean.CandyList;
import com.cai.work.bean.respond.CandyListRespond;
import com.example.clarence.netlibrary.NetRespondCallBack;

import org.reactivestreams.Subscription;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.functions.Consumer;

public class CandyPresenter extends AppBasePresenter<CandyView> {
    @Inject
    public CandyPresenter() {
    }

    @Override
    public void onAttached() {

    }

    public void requestCandyList() {
        requestStore.get().questCandyList()
                .doOnNext(new Consumer<CandyListRespond>() {
                    @Override
                    public void accept(CandyListRespond candyListRespond) {
                        if (candyListRespond.getErrorcode() == 0) {
                            List<CandyList> candyList = candyListRespond.getData();
                            if (candyList != null) {
                                cacheStore.get().saveCandyList(candyList);
                            }
                        }
                    }
                }).subscribe(new NetRespondCallBack<CandyListRespond>() {
            @Override
            public void respondResult(Subscription subscription, CandyListRespond respond) {
                if (respond.getErrorcode() == 0) {
                    mView.callBack(respond.getData());
                } else {
                    mView.callBack(respond.getMessage());
                }
            }

            @Override
            public void respondError(Throwable t) {
                mView.callBack(t.getMessage());
            }
        });
    }

}
