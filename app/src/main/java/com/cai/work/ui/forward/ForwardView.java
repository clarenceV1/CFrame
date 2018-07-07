package com.cai.work.ui.forward;

import com.cai.framework.base.GodBaseView;
import com.cai.work.bean.Forward;
import com.cai.work.bean.ForwardRecord;
import com.cai.work.bean.News;
import com.cai.work.bean.Record;

import java.util.List;

public interface ForwardView extends GodBaseView {

    void toast(String msg,int type);

    void callBack(ForwardRecord forwardRecord);

    void callBack(List<Forward> forwardList);

    void callBack(String[][] data, String resolution);
}
