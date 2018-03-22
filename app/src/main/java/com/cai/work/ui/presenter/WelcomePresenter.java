package com.cai.work.ui.presenter;

import com.cai.annotation.apt.InstanceFactory;
import com.cai.framework.base.BaseLifecycleObserver;
import com.cai.framework.base.GodBasePresenter;

/**
 * Created by clarence on 2018/1/12.
 */
@InstanceFactory
public class WelcomePresenter extends GodBasePresenter<WelcomeView> {

    @Override
    public void onAttached() {
        mView.setContent("我是欢迎页面");
    }
    @Override
    public void ON_CREATE() {
        data.put(BaseLifecycleObserver.CLASS_NAME, "WelcomeActivity");
        super.ON_CREATE();
    }
}
