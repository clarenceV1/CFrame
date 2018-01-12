package com.cai.work.ui.presenter;

import com.cai.framework.base.BasePresenter;

/**
 * Created by clarence on 2018/1/12.
 */

public class LoginPresenter extends BasePresenter<LoginView> {
    @Override
    public void onAttached() {
        mView.setLoginContent("我是登录页面");
    }
}
