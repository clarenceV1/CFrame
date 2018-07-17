package com.meetone.work.ui.welfare;

import com.meetone.work.bean.Welfare;

import java.util.List;

public interface WelfareView {
    void callBack(List<Welfare> welfareList);

    void callBack(String message);
}
