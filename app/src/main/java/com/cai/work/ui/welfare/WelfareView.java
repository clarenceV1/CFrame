package com.cai.work.ui.welfare;

import com.cai.work.bean.Welfare;

import java.util.List;

public interface WelfareView {
    void callBack(List<Welfare> welfareList);

    void callBack(String message);
}
