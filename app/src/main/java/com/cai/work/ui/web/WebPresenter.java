package com.cai.work.ui.web;

import com.cai.framework.base.GodBaseConfig;
import com.cai.framework.base.GodBasePresenter;
import com.cai.work.dao.AccountDAO;
import com.example.clarence.utillibrary.StringUtils;

import javax.inject.Inject;

/**
 * Created by clarence on 2018/1/12.
 */
public class WebPresenter extends GodBasePresenter<WebForRTB> {
    @Inject
    AccountDAO accountDAO;

    @Inject
    public WebPresenter() {
    }

    @Override
    public void onAttached() {
    }
}
