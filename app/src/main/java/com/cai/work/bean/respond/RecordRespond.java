package com.cai.work.bean.respond;

import com.cai.work.bean.RecordDataModel;

public class RecordRespond extends BaseRespond{
    private RecordDataModel data;

    public RecordDataModel getData() {
        return data;
    }

    public void setData(RecordDataModel data) {
        this.data = data;
    }
}
