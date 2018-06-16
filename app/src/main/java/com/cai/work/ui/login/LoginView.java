package com.cai.work.ui.login;

import com.cai.framework.base.GodBaseView;

/**
 * Created by clarence on 2018/1/12.
 */

public interface LoginView extends GodBaseView {
        void loginSuccess();
        void toast(int type, String msg);
}
