package com.cai.work.ui.rebate;

import com.cai.framework.base.GodBasePresenter;
import com.cai.lib.logger.Logger;
import com.cai.work.bean.RebateItem;
import com.cai.work.bean.respond.CommonRespond;
import com.cai.work.bean.respond.RebateRespond;
import com.cai.work.common.DataStore;
import com.cai.work.common.RequestStore;
import com.cai.work.dao.AccountDAO;
import com.cai.work.dao.UserDAO;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class RebatePresenter extends GodBasePresenter<RebateView> {

    @Inject
    RequestStore requestStore;
    @Inject
    DataStore dataStore;
    @Inject
    UserDAO userDAO;
    @Inject
    AccountDAO accountDAO;

    @Inject
    public RebatePresenter() {
    }

    @Override
    public void onAttached() {

    }

    public void requestData() {
        String token = accountDAO.getToken();
        Disposable disposable = requestStore.requestRebate(token, new Consumer<RebateRespond>() {
            @Override
            public void accept(RebateRespond data) {
                if (data != null && data.getCode() == 200) {
                    mView.update(data.getData());
                } else {
                    mView.toast(data.getResponseText());
                }
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) {
                mView.toast("请求异常");
            }
        });
        mCompositeSubscription.add(disposable);
    }

    public void commit(List<RebateItem> dataList) {
        String token = accountDAO.getToken();
        String ids = null;
        if (dataList != null) {
            StringBuilder idsBuilder = new StringBuilder();
            for (RebateItem rebateItem : dataList) {
                if (rebateItem.isChoosed()) {
                    idsBuilder.append(rebateItem.getId()).append(",");
                }
            }
            ids = idsBuilder.toString();
            if (ids.contains(",") && ids.length() > 1) {
                ids = ids.substring(0,ids.length() - 1);
            }
        }
        if (ids == null) {
            return;
        }
        Disposable disposable = requestStore.requestWithdrawRebate(ids, token, new Consumer<CommonRespond>() {
            @Override
            public void accept(CommonRespond data) {
                if (data != null && data.getCode() == 200) {
                    mView.toast("提交成功");
                } else {
                    mView.toast(data.getResponseText());
                }
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) {
                mView.toast("请求异常");
            }
        });
        mCompositeSubscription.add(disposable);
    }
}
