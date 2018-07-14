package com.cai.work.ui.bank;

import android.content.res.AssetManager;
import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.cai.framework.base.GodBasePresenter;
import com.cai.lib.logger.Logger;
import com.cai.work.bean.Province;
import com.cai.work.bean.respond.BankListRespond;
import com.cai.work.bean.respond.BaseRespond;
import com.cai.work.common.DataStore;
import com.cai.work.common.RequestStore;
import com.cai.work.dao.AccountDAO;
import com.example.clarence.utillibrary.NetWorkUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class AddBankCardPresenter extends GodBasePresenter<AddBankCardView> {

    @Inject
    RequestStore requestStore;
    @Inject
    DataStore dataStore;
    @Inject
    AccountDAO accountDAO;

    @Inject
    public AddBankCardPresenter() {
    }

    @Override
    public void onAttached() {

    }

    public void postBankInfo(String realname, String cardNo, String bankId, String branchName) {
        String token = accountDAO.getToken();
        Disposable disposable = requestStore.requestAddBankCard(realname, cardNo, bankId, branchName, token, new Consumer<BaseRespond>() {
            @Override
            public void accept(BaseRespond data) {
                if (data != null) {
                    mView.refreshView(data.getResponseText());
                }
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) {
                if (NetWorkUtil.isNetConnected(context)) {
                    Logger.d("请求添加银行卡数据失败！！！---有网络");
                } else {
                    Logger.d("请求添加银行卡数据失败！！！---没网络");
                }
            }
        });
        mCompositeSubscription.add(disposable);
    }

    public void getBankList(final boolean showDialog) {
        String token = accountDAO.getToken();
        Disposable disposable = requestStore.getBankList(token, new Consumer<BankListRespond>() {
            @Override
            public void accept(BankListRespond data) {
                if (data != null && data.getCode() == 200) {
                    mView.getBankList(data.getData(), showDialog);
                }
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) {
                if (NetWorkUtil.isNetConnected(context)) {
                    Logger.d("请求银行列表数据失败！！！---有网络");
                } else {
                    Logger.d("请求银行列表数据失败！！！---没网络");
                }
            }
        });
        mCompositeSubscription.add(disposable);
    }

    public void loadCitys() {
        Disposable disposable = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> ex) {
                StringBuilder sb = new StringBuilder();
                String json = "";
                try {
                    AssetManager am = context.getAssets();
                    BufferedReader br = new BufferedReader(new InputStreamReader(am.open("city.json")));
                    String next = "";
                    while (null != (next = br.readLine())) {
                        sb.append(next);
                    }
                    json = sb.toString().trim();
                } catch (Exception e) {
                    e.printStackTrace();
                    sb.delete(0, sb.length());
                }
                ex.onNext(json);
            }
        }).map(new Function<String, List<Province>>() {
            @Override
            public List<Province> apply(String json) {
                List<Province> list = new ArrayList<>();
                if (!TextUtils.isEmpty(json)) {
                    list = JSON.parseArray(json, Province.class);
                }
                return list;
            }
        }).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Province>>() {
                    @Override
                    public void accept(List<Province> provinces){
                        if (provinces != null) {
                            mView.showCityDialog(provinces);
                        }
                    }
                });
        mCompositeSubscription.add(disposable);
    }
}
