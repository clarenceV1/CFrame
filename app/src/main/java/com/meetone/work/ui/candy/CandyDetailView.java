package com.meetone.work.ui.candy;

import com.meetone.work.bean.CandyDetailModel;

public interface CandyDetailView {
    void callback(String message, int type);

    void callback(CandyDetailModel candyDetailModel);
}
