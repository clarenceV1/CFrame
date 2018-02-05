package com.cai.work.ui.presenter;

import com.cai.annotation.apt.InstanceFactory;
import com.cai.framework.base.CBasePresenter;

/**
 * Created by clarence on 2018/1/12.
 */
@InstanceFactory
public class LoginPresenter extends CBasePresenter<LoginView> {
    @Override
    public void onAttached() {
        mView.setLoginContent("我是登录页面");
    }

    public void login() {

    }
}
