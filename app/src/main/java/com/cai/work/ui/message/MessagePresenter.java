package com.cai.work.ui.message;

import com.cai.framework.base.GodBasePresenter;
import com.cai.lib.logger.Logger;
import com.cai.work.bean.Message;
import com.cai.work.bean.MessageItem;
import com.cai.work.bean.respond.BaseRespond;
import com.cai.work.bean.respond.MessageRespond;
import com.cai.work.common.DataStore;
import com.cai.work.common.RequestStore;
import com.cai.work.dao.AccountDAO;
import com.example.clarence.utillibrary.NetWorkUtil;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class MessagePresenter extends GodBasePresenter<MessageView> {

    @Inject
    RequestStore requestStore;
    @Inject
    DataStore dataStore;
    @Inject
    AccountDAO accountDAO;

    @Inject
    public MessagePresenter() {
    }

    @Override
    public void onAttached() {

    }

    public void getMessage(int page) {
        String token = accountDAO.getToken();
        Disposable disposable = requestStore.getMessage(page, token, new Consumer<MessageRespond>() {
            @Override
            public void accept(MessageRespond data) {
                if (data != null && data.getCode() == 200) {
                    mView.refreshMessageList(data.getData());
                }
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) {
                if (NetWorkUtil.isNetConnected(context)) {
                    Logger.d("请求消息列表数据失败！！！---有网络");
                } else {
                    Logger.d("请求消息列表数据失败！！！---没网络");
                }
            }
        });
        mCompositeSubscription.add(disposable);
    }

    private void addTestMsg(Message message) {
        message.setTotal(10);
        message.setTotal_page(2);
        List<MessageItem> data = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            MessageItem item = new MessageItem();
            item.setContent("我是测试数据==>" + i);
            item.setId(i);
            item.setCreateTime("1928-09-22 11:20");
            item.setMsgType(i % 2 == 0 ? 1 : 2);
            item.setReadType(i % 2 == 0 ? 1 : 2);
            data.add(item);
        }
        message.setData(data);
    }

    public void deleteMessage(int ids) {
        String token = accountDAO.getToken();
        Disposable disposable = requestStore.deleteMessage(ids, token, new Consumer<BaseRespond>() {
            @Override
            public void accept(BaseRespond data) {
                if (data != null && data.getCode() == 200) {
                    mView.showToast(data.getResponseText());
                }
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) {
                if (NetWorkUtil.isNetConnected(context)) {
                    Logger.d("请求删除消息失败！！！---有网络");
                } else {
                    Logger.d("请求删除消息失败！！！---没网络");
                }
            }
        });
        mCompositeSubscription.add(disposable);
    }

    public void deleteMessageAll() {
        String token = accountDAO.getToken();
        Disposable disposable = requestStore.deleteMessageAll(token, new Consumer<BaseRespond>() {
            @Override
            public void accept(BaseRespond data) {
                if (data != null) {
                    mView.showToast(data.getResponseText());
                }
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) {
                if (NetWorkUtil.isNetConnected(context)) {
                    Logger.d("请求删除消息失败！！！---有网络");
                } else {
                    Logger.d("请求删除消息失败！！！---没网络");
                }
            }
        });
        mCompositeSubscription.add(disposable);
    }
}
