package com.cai.work.ui.presenter;

import com.cai.annotation.apt.InstanceFactory;
import com.cai.framework.base.CBasePresenter;
import com.cai.framework.http.Api;
import com.cai.work.ApiService;

/**
 * Created by clarence on 2018/1/12.
 */
@InstanceFactory
public class MainPresenter extends CBasePresenter<MainView> {
    @Override
    public void onAttached() {
        mView.setMainContent("我是主页面");
        getContent();
    }

    public void getContent() {
        ApiService apiService = Api.getInstance().request().create(ApiService.class);
        apiService.login();
    }
}
