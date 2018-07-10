package com.cai.work.ui.nationcode;

import com.cai.work.bean.NationCodeModel;

import java.util.List;

public interface NationCodeView {
    void callback(List<NationCodeModel> data, boolean isChinese);

    void callback(String message);

}
