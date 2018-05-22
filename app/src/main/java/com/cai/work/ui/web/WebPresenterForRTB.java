package com.cai.work.ui.web;

import com.alibaba.android.arouter.launcher.ARouter;
import com.cai.framework.base.GodBasePresenter;

import javax.inject.Inject;

/**
 * Created by clarence on 2018/1/12.
 */
public class WebPresenterForRTB extends GodBasePresenter<WebForRTB> {
    @Inject
    public WebPresenterForRTB() {
    }

    @Override
    public void onAttached() {
    }

    public void goToMain() {
        ARouter.getInstance().build("/AppModule/MainActivity").navigation();
    }
}
