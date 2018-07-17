package com.meetone.work.bean;

import java.util.List;

/**
 * Created by davy on 2018/3/22.
 */

public class RecordDataModel extends BaseModel {
    private List<RecordModel> record;
    private int lastid;

    public List<RecordModel> getRecord() {
        return record;
    }

    public void setRecord(List<RecordModel> record) {
        this.record = record;
    }

    public int getLastid() {
        return lastid;
    }

    public void setLastid(int lastid) {
        this.lastid = lastid;
    }
}
