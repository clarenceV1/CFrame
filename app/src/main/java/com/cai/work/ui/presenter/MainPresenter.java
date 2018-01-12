package com.cai.work.ui.presenter;

import com.cai.annotation.apt.InstanceFactory;
import com.cai.framework.base.CBasePresenter;

/**
 * Created by clarence on 2018/1/12.
 */
@InstanceFactory
public class MainPresenter extends CBasePresenter<MainView> {
    @Override
    public void onAttached() {
        mView.setMainContent("我是主页面");
    }
}
