package com.cai.work.ui.record;

import com.cai.work.bean.RecordDataModel;

public interface RecordView {

    void callBack(RecordDataModel data, int lastid);

    void callBack(String message);
}
