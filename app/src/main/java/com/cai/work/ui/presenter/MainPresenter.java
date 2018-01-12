package com.cai.work.ui.presenter;

import com.cai.framework.base.BasePresenter;

/**
 * Created by clarence on 2018/1/12.
 */

public class MainPresenter extends BasePresenter<MainView> {
    @Override
    public void onAttached() {
        mView.setMainContent("我是主页面");
    }
}
