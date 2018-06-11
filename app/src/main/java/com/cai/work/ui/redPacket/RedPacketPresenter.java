package com.cai.work.ui.redPacket;

import com.cai.framework.base.GodBasePresenter;
import com.cai.lib.logger.Logger;
import com.cai.work.bean.Message;
import com.cai.work.bean.MessageItem;
import com.cai.work.bean.RedPacket;
import com.cai.work.bean.RedPacketItem;
import com.cai.work.bean.respond.BaseRespond;
import com.cai.work.bean.respond.MessageRespond;
import com.cai.work.bean.respond.RedPacketRespond;
import com.cai.work.common.DataStore;
import com.cai.work.common.RequestStore;
import com.cai.work.dao.AccountDAO;
import com.cai.work.ui.message.MessageView;
import com.example.clarence.utillibrary.NetWorkUtil;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class RedPacketPresenter extends GodBasePresenter<RedPacketView> {

    @Inject
    RequestStore requestStore;
    @Inject
    DataStore dataStore;
    @Inject
    AccountDAO accountDAO;

    @Inject
    public RedPacketPresenter() {
    }

    @Override
    public void onAttached() {

    }

    public void getRedPacket(int page) {
        String token = accountDAO.getToken();
        Disposable disposable = requestStore.getRedPacket(page, token, new Consumer<RedPacketRespond>() {
            @Override
            public void accept(RedPacketRespond data) {
                if (data != null && data.getCode() == 200) {
                    addTestMsg(data.getData());
                    mView.refreshData(data.getData());
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

    private void addTestMsg(RedPacket redPacket) {
        redPacket.setTotal(10);
        redPacket.setTotal_page(2);
        List<RedPacketItem> data = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            RedPacketItem item = new RedPacketItem();
            item.setParValue(i + ".00");
            item.setDueDate("2018-06-03");
            item.setRedText("全民红包");
            item.setStatus(i % 3 + 1);
            data.add(item);
        }
        redPacket.setData(data);
    }
}
