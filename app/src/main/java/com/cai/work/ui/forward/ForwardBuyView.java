package com.cai.work.ui.forward;

import com.cai.framework.base.GodBaseView;
import com.cai.work.bean.ForwardBuy;

public interface ForwardBuyView extends GodBaseView {

    void callBack(ForwardBuy data);

    void toast(String msg);

    void kaiCangSuccess();
}
