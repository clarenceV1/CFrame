package com.cai.work.ui.main;

import com.cai.framework.base.GodBasePresenter;
import com.cai.work.base.App;

import javax.inject.Inject;

/**
 * Created by clarence on 2018/1/12.
 */
public class MainPresenter extends GodBasePresenter<MainView> {

    @Inject
    public MainPresenter() {

    }

    @Override
    public void onAttached() {
        App.getAppComponent().inject(this);
    }

}
