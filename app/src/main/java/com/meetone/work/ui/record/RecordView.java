package com.meetone.work.ui.record;

import com.meetone.work.bean.RecordDataModel;

public interface RecordView {

    void callBack(RecordDataModel data, int lastid);

    void callBack(String message);
}
