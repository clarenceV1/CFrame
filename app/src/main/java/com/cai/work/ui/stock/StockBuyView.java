package com.cai.work.ui.stock;

import com.cai.framework.base.GodBaseView;
import com.cai.work.bean.Stock;
import com.cai.work.bean.StockBuy;
import com.cai.work.bean.StockHQ;
import com.cai.work.bean.StockTrade;

import java.util.List;

public interface StockBuyView extends GodBaseView {

    void callBack(StockBuy data);

    void callBack(String msg);

    void buySuccess();

}
