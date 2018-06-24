package com.cai.work.ui.stock;

import com.cai.framework.base.GodBaseView;
import com.cai.work.bean.Stock;
import com.cai.work.bean.StockHQ;
import com.cai.work.bean.StockTrade;
import com.cai.work.bean.respond.StockTradeRespond;

import java.util.List;

public interface StockView extends GodBaseView {

    void toast(String msg, int type);

    void callBack(StockTrade data);

    void callBack(StockHQ data);

    void callBack(List<Stock> data);

    void callBack(String[][] data);
}
