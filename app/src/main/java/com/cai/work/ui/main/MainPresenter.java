package com.cai.work.ui.main;

import com.cai.work.base.App;
import com.cai.work.base.AppBasePresenter;

import javax.inject.Inject;

/**
 * Created by clarence on 2018/1/12.
 */
public class MainPresenter extends AppBasePresenter<MainView> {

    @Inject
    public MainPresenter() {

    }

    @Override
    public void onAttached() {
        App.getAppComponent().inject(this);
    }

}
