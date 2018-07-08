package com.cai.work.ui.forward;

import android.text.TextUtils;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.cai.framework.base.GodBasePresenter;
import com.cai.lib.logger.Logger;
import com.cai.work.bean.Forward;
import com.cai.work.bean.ForwardDetail;
import com.cai.work.bean.ForwardHold;
import com.cai.work.bean.ForwardRecord;
import com.cai.work.bean.Record;
import com.cai.work.bean.respond.ForwardContractsRespond;
import com.cai.work.common.RequestStore;
import com.cai.work.dao.AccountDAO;
import com.cai.work.event.ForwardDetailEvent;
import com.cai.work.event.ForwardHoldEvent;
import com.cai.work.socket.SocketManager;
import com.example.clarence.utillibrary.StringUtils;
import com.koushikdutta.async.http.WebSocket;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
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
        Disposable disposable = requestStore.requestRecord(token, code, new Consumer<ForwardRecord>() {
            @Override
            public void accept(ForwardRecord forwardRecord) {
                mView.callBack(forwardRecord);
                SocketManager.init(forwardRecord.getSocket_host(), forwardRecord.getSocket_port(), new WebSocket.StringCallback() {
                    @Override
                    public void onStringAvailable(String json) {
                        json = StringUtils.replaceBlank(json);
                        Log.d("requestRecord", "ip: " + SocketManager.mIp + "===>" + json);
                        if (!TextUtils.isEmpty(json) && !"[]".equals(json)) {
                            ForwardDetail detail = JSON.parseObject(json, ForwardDetail.class);
                            EventBus.getDefault().post(new ForwardDetailEvent(detail));
                        }
                    }
                });
                startTimes(forwardRecord);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) {
                mView.toast("请求异常", 3);
            }
        });
        mCompositeSubscription.add(disposable);
    }

    private void startTimes(final ForwardRecord forwardRecord) {
        SocketManager.connect();
        startSocket(forwardRecord);
//        Disposable disposable = Observable.interval(0,3, TimeUnit.SECONDS)
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Consumer<Long>() {
//                    @Override
//                    public void accept(Long aLong) throws Exception {
//
//                    }
//                });
//        mCompositeSubscription.add(disposable);
    }

    private void startSocket(ForwardRecord forwardRecord) {
        Record record = forwardRecord.getRecords();
        if (record != null) {
            StringBuilder builder = new StringBuilder();
            builder.append("hq|");
            if (record.getType() == 1) {
                if (record.getAttributeType() == 1) {
                    builder.append("sp|");
                } else {
                    builder.append("gz|");
                }
            } else {
                builder.append("wp|");
            }
            SocketManager.sendSocket(builder.toString() + record.getContractCode());
        }
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
                mView.toast(throwable.getMessage(), 2);
            }
        });
        mCompositeSubscription.add(disposable);
    }

    public void requestMinData(String code, final String resolution) {
        Disposable disposable = requestStore.requestMinData(code, resolution, new Consumer<String[][]>() {
            @Override
            public void accept(String[][] data) {
                if (data != null) {
                    Log.d("datum", data.toString());
                    mView.callBack(data, resolution);
                }
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) {
                mView.toast(throwable.getMessage(), 2);
            }
        });
        mCompositeSubscription.add(disposable);
    }
}
