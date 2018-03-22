package com.cai.work.ui.lifecycleobserver;

import com.cai.annotation.apt.InstanceFactory;
import com.cai.framework.base.BaseLifecycleObserver;
import com.cai.framework.log.LogUtils;

/**
 * Created by clarence on 2018/3/22.
 */
@InstanceFactory
public class WelcomeObserver extends BaseLifecycleObserver {

    @Override
    public void ON_CREATE() {
        data.put(BaseLifecycleObserver.CLASS_NAME, "MainActivity");
        super.ON_CREATE();
        LogUtils.getInsatance().debug("LifecycleObserver", "WelcomeObserver", ":ON_CREATE");
    }
}
