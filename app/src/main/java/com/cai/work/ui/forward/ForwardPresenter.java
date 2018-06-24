package com.cai.work.ui.forward;

import android.util.Log;

import com.cai.framework.base.GodBasePresenter;
import com.cai.lib.logger.Logger;
import com.cai.work.bean.Forward;
import com.cai.work.bean.ForwardRecord;
import com.cai.work.bean.respond.ForwardContractsRespond;
import com.cai.work.common.RequestStore;
import com.cai.work.dao.AccountDAO;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class ForwardPresenter extends GodBasePresenter<ForwardView> {

    @Inject
    RequestStore requestStore;
    @Inject
    AccountDAO accountDAO;

    @Inject
    public ForwardPresenter() {
    }

    @Override
    public void onAttached() {

    }

    public void requestRecord(String code) {
        String token = accountDAO.getToken();
        Disposable disposable = requestStore.requestRecord(token, code,new Consumer<ForwardRecord>() {
            @Override
            public void accept(ForwardRecord data) {
                mView.callBack(data);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) {
                mView.toast("请求异常", 3);
            }
        });
        mCompositeSubscription.add(disposable);
    }

    public void requestContracts() {
        String token = accountDAO.getToken();
        Disposable disposable = requestStore.requestContracts(token, new Consumer<ForwardContractsRespond>() {
            @Override
            public void accept(ForwardContractsRespond data) {
                if (data.getCode() == 200 && data.getData() != null) {
                    List<Forward> forwardList = new ArrayList<>();
                    Map<String, String> map = data.getData();
                    for (Map.Entry<String, String> entry : map.entrySet()) {
                        String key = entry.getKey();
                        String value = entry.getValue();
                        Forward forward = new Forward(value, key);
                        forwardList.add(forward);
                    }
                    mView.callBack(forwardList);
                } else {
                    mView.toast(data.getResponseText(), 1);
                }
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) {
                mView.toast("请求异常", 2);
            }
        });
        mCompositeSubscription.add(disposable);
    }
}
