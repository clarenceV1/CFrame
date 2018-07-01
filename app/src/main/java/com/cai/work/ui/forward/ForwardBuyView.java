package com.cai.work.ui.forward;

import com.cai.framework.base.GodBaseView;
import com.cai.work.bean.Forward;
import com.cai.work.bean.ForwardBuy;
import com.cai.work.bean.ForwardRecord;

import java.util.List;

public interface ForwardBuyView extends GodBaseView {

    void callBack(ForwardBuy data);

    void toast(String msg);
}
