package com.cai.work.ui.presenter;

import com.cai.annotation.apt.InstanceFactory;
import com.cai.framework.base.CBasePresenter;

/**
 * Created by clarence on 2018/1/12.
 */
@InstanceFactory
public class WelcomePresenter extends CBasePresenter<WelcomeView> {

    @Override
    public void onAttached() {
        mView.setContent("我是欢迎页面");
    }
}
