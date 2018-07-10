package com.cai.work.ui.stock;

import com.cai.framework.base.GodBasePresenter;
import com.cai.work.bean.respond.CommonRespond;
import com.cai.work.bean.respond.StockBuyRespond;
import com.cai.work.bean.respond.StockHQRespond;
import com.cai.work.bean.respond.StockListRespond;
import com.cai.work.bean.respond.StockTradeRespond;
import com.cai.work.common.RequestStore;
import com.cai.work.dao.AccountDAO;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class StockBuyPresenter extends GodBasePresenter<StockBuyView> {

    @Inject
    RequestStore requestStore;
    @Inject
    AccountDAO accountDAO;

    @Inject
    public StockBuyPresenter() {
    }

    @Override
    public void onAttached() {

    }

    public void requestData(String code) {
        String token = accountDAO.getToken();
        Disposable disposable = requestStore.requestStockBuy(code, token, new Consumer<StockBuyRespond>() {
            @Override
            public void accept(StockBuyRespond data) {
                mView.callBack(data.getData());
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) {
                mView.callBack(throwable.getMessage());
            }
        });
        mCompositeSubscription.add(disposable);
    }

    /**
     * @param code       股票代码
     * @param name       股票名称
     * @param marketType 市场类别
     * @param price      价格
     * @param amount     数量
     * @param principal  点买金额
     * @param bzj        保证金
     * @param zy         止盈
     * @param zs         止损
     * @param redbagIds  红包id
     * @param zhf        综合费
     */
    public void commitBuy(String code, String name, String marketType, String price, String amount, String principal,
                          String bzj, String zy, String zs, String redbagIds, String zhf) {
        String token = accountDAO.getToken();
        Disposable disposable = requestStore.commitStockBuy(token, code, name, marketType, price, amount, principal,
                bzj, zy, zs, redbagIds, zhf, new Consumer<CommonRespond>() {
                    @Override
                    public void accept(CommonRespond data) {
                        mView.callBack(data.getResponseText());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) {
                        mView.callBack("错误");
                    }
                });
        mCompositeSubscription.add(disposable);
    }
}
