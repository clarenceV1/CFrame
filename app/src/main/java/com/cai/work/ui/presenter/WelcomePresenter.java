package com.cai.work.ui.presenter;

import com.cai.framework.base.BasePresenter;

/**
 * Created by clarence on 2018/1/12.
 */
public class WelcomePresenter extends BasePresenter<WelcomeView> {

    @Override
    public void onAttached() {
        mView.setContent("我是欢迎页面");
    }
}