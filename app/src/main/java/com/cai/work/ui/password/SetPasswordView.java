package com.cai.work.ui.password;

import com.cai.framework.base.GodBaseView;

/**
 * Created by clarence on 2018/1/12.
 */

public interface SetPasswordView extends GodBaseView {
        void getMobile(String mobile);
        void toast(int type, String msg);
}
