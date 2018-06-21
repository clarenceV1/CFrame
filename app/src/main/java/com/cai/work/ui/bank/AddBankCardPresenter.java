package com.cai.work.ui.bank;

import com.cai.framework.base.GodBasePresenter;
import com.cai.lib.logger.Logger;
import com.cai.work.bean.respond.BankListRespond;
import com.cai.work.bean.respond.BaseRespond;
import com.cai.work.common.DataStore;
import com.cai.work.common.RequestStore;
import com.cai.work.dao.AccountDAO;
import com.example.clarence.utillibrary.NetWorkUtil;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

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
                if (data != null && data.getCode() ==200) {
                    mView.getBankList(data.getData(),showDialog);
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
}