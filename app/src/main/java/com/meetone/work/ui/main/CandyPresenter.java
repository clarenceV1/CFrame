package com.meetone.work.ui.main;

import android.content.Intent;
import android.text.TextUtils;

import com.alibaba.android.arouter.launcher.ARouter;
import com.meetone.work.R;
import com.example.clarence.netlibrary.NetRespondCallBack;
import com.example.clarence.utillibrary.StringUtils;
import com.meetone.work.base.AppBasePresenter;
import com.meetone.work.bean.CandyList;
import com.meetone.work.bean.respond.CandyListRespond;
import com.meetone.work.bean.respond.Respond;

import org.reactivestreams.Subscription;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class CandyPresenter extends AppBasePresenter<CandyView> {
    @Inject
    public CandyPresenter() {
    }

    @Override
    public void onAttached() {

    }

    public void requestCandyList(boolean isloadCache) {
        if (isloadCache) {
            getCandyListOfCache();
        }
        getCandyListOfNet();
    }

    public void getCandyListOfCache() {
        Disposable disposable = Observable.create(new ObservableOnSubscribe<List<CandyList>>() {
            @Override
            public void subscribe(ObservableEmitter<List<CandyList>> e) {
                List<CandyList> candyList = cacheStore.get().getCandyList();
                if (candyList == null) {
                    candyList = new ArrayList<>();//rxjava 不允许传null
                }
                e.onNext(candyList);
            }
        }).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<CandyList>>() {
                    @Override
                    public void accept(List<CandyList> candyList) {
                        if (candyList != null && candyList.size() > 0) {
                            mView.callBack(candyList);
                        }
                    }
                });
        mCompositeSubscription.add(disposable);
    }

    public void saveCache(final List<CandyList> datas) {
        Disposable disposable = Observable.create(new ObservableOnSubscribe<List<CandyList>>() {
            @Override
            public void subscribe(ObservableEmitter<List<CandyList>> e) throws Exception {

            }
        }).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<CandyList>>() {
                    @Override
                    public void accept(List<CandyList> candyLists) throws Exception {
                        if (datas != null) {
                            cacheStore.get().saveCandyList(datas);
                        }
                    }
                });
        mCompositeSubscription.add(disposable);
    }

    public void getCandyListOfNet() {
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
                }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new NetRespondCallBack<CandyListRespond>() {
                    @Override
                    public void respondResult(Subscription subscription, CandyListRespond respond) {
                        if (respond.getErrorcode() == 0) {
                            mView.callBack(respond.getData());
                        } else {
                            mView.callBack(new ArrayList<CandyList>());
                            mView.callBack(respond.getMessage());
                        }
                    }

                    @Override
                    public void respondError(Throwable t) {
                        mView.callBack(t.getMessage());
                        mView.callBack(new ArrayList<CandyList>());
                    }
                });
    }

    /**
     * 领取糖果
     */
    public void receiveCandy(final int tokenId) {
        Map<String, String> params = new HashMap<>();
        params.put("token_id", tokenId + "");
        requestStore.get().receiveCandy(params)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new NetRespondCallBack<Respond>() {
                    @Override
                    public void respondResult(Subscription subscription, Respond respond) {
                        if (respond.getErrorcode() == 0) {
                            mView.receiveCoinSuccess(tokenId);
                            mView.callBack(context.getResources().getString(R.string.candy_receive_success));
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

    public void clickReceiveCoinBtn(CandyList candyList) {
        if (userDAO.get().isLogin()) { //login
            if (candyList.getGive_total() > 0) {
                receiveCandy(candyList.getToken_id());
                getStatistics().home_lq();
            } else {//查看更多
                shareAll(getShareText());
                getStatistics().home_lqgd();
            }
        } else { //logout
            ARouter.getInstance().build("/MeetOne/LoginActivity").navigation();
        }
    }

    public void shareAll(String shareText) {
        try {
            if (!TextUtils.isEmpty(shareText)) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT, shareText);
                context.startActivity(Intent.createChooser(intent, ""));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getShareText() {
        String url = dataStore.get().getInviteUrl();
        String title = dataStore.get().getInviteTitle();
        if (StringUtils.isEmpty(url)) {
            return null;
        } else {
            String shareText = "More.one";
            if (TextUtils.isEmpty(title)) {
                shareText = title + url;
            } else {
                shareText = context.getString(R.string.share_text) + url;
            }
            return shareText;
        }

    }
}
