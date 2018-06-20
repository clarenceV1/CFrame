package com.cai.work.ui.welcome;

import com.cai.framework.base.GodBaseView;

/**
 * Created by clarence on 2018/1/12.
 */

public interface WelcomeView extends GodBaseView {

    void toastNotice(String txt);
    void appUpdate();
}
