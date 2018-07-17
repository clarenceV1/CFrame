package com.meetone.work.ui.main;

import com.meetone.work.bean.CandyList;

import java.util.List;

public interface CandyView {
    void callBack(String msg);

    void callBack(List<CandyList> data);

    void receiveCoinSuccess(int token_id);
}
