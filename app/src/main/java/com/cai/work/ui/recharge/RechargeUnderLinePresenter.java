package com.cai.work.ui.recharge;

import com.cai.framework.base.GodBasePresenter;
import com.cai.lib.logger.Logger;
import com.cai.work.R;
import com.cai.work.bean.RechargeBank;
import com.cai.work.bean.respond.BaseRespond;
import com.cai.work.bean.respond.RechargeBankResond;
import com.cai.work.common.DataStore;
import com.cai.work.common.RequestStore;
import com.cai.work.dao.AccountDAO;
import com.cai.work.dao.UserDAO;
import com.example.clarence.utillibrary.NetWorkUtil;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class RechargeUnderLinePresenter extends GodBasePresenter<RechargeUnderLineView> {

    @Inject
    RequestStore requestStore;
    @Inject
    DataStore dataStore;
    @Inject
    UserDAO userDAO;
    @Inject
    AccountDAO accountDAO;

    @Inject
    public RechargeUnderLinePresenter() {
    }

    @Override
    public void onAttached() {

    }


    public void requestRechargeBankList() {
        String token = accountDAO.getToken();
        final Disposable disposable = requestStore.requestRechargeBankList(token, new Consumer<RechargeBankResond>() {
            @Override
            public void accept(RechargeBankResond data) {
                if (data != null && data.getCode() == 200) {
                    List<RechargeBank> dataList = data.getData();
                    if (dataList != null && dataList.size() > 0) {
                        dataList.get(0).setCheck(true);
                    }
                    mView.updateListView(dataList);
                }
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) {
                if (NetWorkUtil.isNetConnected(context)) {
                    Logger.d("请求资金明细数据失败！！！---有网络");
                } else {
                    Logger.d("请求资金明细数据失败！！！---没网络");
                }
            }
        });
        mCompositeSubscription.add(disposable);
    }

    public void commitPay(String offlineName, String amount, int offlineId, String offlineAccount, String offlineImageUrl) {
        String token = accountDAO.getToken();
        Disposable disposable = requestStore.commitPay(offlineName, amount, offlineId, offlineAccount, offlineImageUrl, token, new Consumer<BaseRespond>() {
            @Override
            public void accept(BaseRespond data) {
                if (data != null && data.getCode() == 200) {
                    mView.payState((context.getResources().getString(R.string.recharge_commit_pay_success)));
                } else {
                    mView.payState(data.getResponseText());
                }
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) {
                if (NetWorkUtil.isNetConnected(context)) {
                    Logger.d("请求资金明细数据失败！！！---有网络");
                } else {
                    Logger.d("请求资金明细数据失败！！！---没网络");
                }
            }
        });
        mCompositeSubscription.add(disposable);
    }
}
