package com.meetone.work.ui.message;

import com.example.clarence.netlibrary.NetRespondCallBack;
import com.meetone.work.base.AppBasePresenter;
import com.meetone.work.bean.Message;
import com.meetone.work.bean.respond.MessageRespond;

import org.reactivestreams.Subscription;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by clarence on 2018/1/12.
 */
public class MessagePresenter extends AppBasePresenter<MessageView> {

    @Inject
    public MessagePresenter() {

    }

    @Override
    public void onAttached() {

    }

    public void loadMsgData(boolean isloadCache) {
        if (isloadCache) {
            getMsgListOfCache();
        }
        gettMsgListOfNet();
    }

    public void getMsgListOfCache() {
        Disposable disposable = Observable.create(new ObservableOnSubscribe<List<Message>>() {
            @Override
            public void subscribe(ObservableEmitter<List<Message>> e) {
                List<Message> messageList = cacheStore.get().getMsgList();
                if (messageList == null) {
                    messageList = new ArrayList<>();//rxjava 不允许传null
                }
                e.onNext(messageList);
            }
        }).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Message>>() {
                    @Override
                    public void accept(List<Message> messageList) {
                        if (messageList != null && messageList.size() > 0) {
                            mView.callBack(messageList);
                        }
                    }
                });
        mCompositeSubscription.add(disposable);
    }

    public void gettMsgListOfNet() {
        requestStore.get().loadMsgData()
                .doOnNext(new Consumer<MessageRespond>() {
                    @Override
                    public void accept(MessageRespond messageRespond) {
                        if (messageRespond.getErrorcode() == 0) {
                            List<Message> messageList = messageRespond.getData();
                            if (messageList != null) {
                                cacheStore.get().saveMsgList(messageList);
                            }
                        }
                    }
                }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new NetRespondCallBack<MessageRespond>() {
                    @Override
                    public void respondResult(Subscription subscription, MessageRespond respond) {
                        if (respond.getErrorcode() == 0) {
                            mView.callBack(respond.getData());
                        } else {
                            mView.callBack(new ArrayList<Message>());
                            mView.callBack(respond.getMessage());
                        }
                    }

                    @Override
                    public void respondError(Throwable t) {
                        mView.callBack(t.getMessage());
                        mView.callBack(new ArrayList<Message>());
                    }
                });
    }
}
