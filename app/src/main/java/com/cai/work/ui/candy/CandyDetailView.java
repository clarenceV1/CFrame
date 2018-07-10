package com.cai.work.ui.candy;

import com.cai.work.bean.CandyDetailModel;

public interface CandyDetailView {
    void callback(String message, int type);

    void callback(CandyDetailModel candyDetailModel);
}
