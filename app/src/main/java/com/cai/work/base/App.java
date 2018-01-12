package com.cai.work.base;

import com.cai.annotation.aspect.CostTime;
import com.cai.framework.base.CBaseApplication;

/**
 * Created by clarence on 2018/1/11.
 */

public class App extends CBaseApplication {

    @CostTime
    public void onCreate() {
        super.onCreate();
    }

}
