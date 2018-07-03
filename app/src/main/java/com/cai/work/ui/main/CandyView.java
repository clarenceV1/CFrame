package com.cai.work.ui.main;

import com.cai.work.bean.CandyList;

import java.util.List;

public interface CandyView {
    void callBack(String msg);

    void callBack(List<CandyList> data);

    void showDialog();
}
