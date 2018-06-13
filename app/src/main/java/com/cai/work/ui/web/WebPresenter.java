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

    public String getToken() {
        return accountDAO.getToken();
    }

    public String getUrl(int payment_way) {
//      http://{domain}/app/h5/recharge/aipay?token=21E80F4D4A6C08F6B3B46620642E34AD&payment_way=1&fromapp=1
        StringBuilder builder = new StringBuilder();
        builder.append(GodBaseConfig.getInstance().getBaseUrl());
        builder.append("/app/h5/recharge/aipay?");
        builder.append("token=").append(getToken());
        builder.append("&fromapp=2");
        builder.append("&payment_way=").append(payment_way);
        return builder.toString();
    }
}
