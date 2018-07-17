package com.meetone.work.bean.respond;


import com.meetone.work.bean.RecordDataModel;

public class RecordRespond extends BaseRespond{
    private RecordDataModel data;

    public RecordDataModel getData() {
        return data;
    }

    public void setData(RecordDataModel data) {
        this.data = data;
    }
}
