package com.cai.work.ui.main.fragment;

import com.cai.framework.base.GodBaseView;
import com.cai.work.bean.Trade;

public interface TradeView extends GodBaseView {
    void update(Trade trade);
    void toast(String msg);
}
