package com.cai.work.ui.main;

import android.content.Intent;
import android.text.TextUtils;

import com.alibaba.android.arouter.launcher.ARouter;
import com.cai.work.R;
import com.cai.work.base.AppBasePresenter;
import com.cai.work.bean.CandyList;
import com.cai.work.bean.respond.CandyListRespond;
import com.example.clarence.netlibrary.NetRespondCallBack;
import com.example.clarence.utillibrary.StringUtils;

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

    /**
     * 领取糖果
     */
    public void receiveCandy(int tokenId) {
        requestStore.get().receiveCandy(tokenId)
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

    public void clickReeceiveCoinBtn(CandyList candyList) {
        if (!TextUtils.isEmpty(userDAO.get().getToken())) { //login
            if (candyList.getGive_total() > 0) {
                mView.showDialog();
                receiveCandy(candyList.getToken_id());
            } else {//查看更多
                shareAll(getShareText());
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
