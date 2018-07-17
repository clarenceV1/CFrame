package com.meetone.work.ui.nationcode;

import com.meetone.work.bean.NationCodeModel;

import java.util.List;

public interface NationCodeView {
    void callback(List<NationCodeModel> data, boolean isChinese);

    void callback(String message);

}
