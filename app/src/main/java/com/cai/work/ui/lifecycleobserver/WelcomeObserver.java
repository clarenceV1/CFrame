package com.cai.work.ui.lifecycleobserver;

import android.arch.lifecycle.Lifecycle;
import android.content.Context;

import com.cai.framework.base.BaseLifecycleObserver;
import com.cai.framework.log.LogUtils;

import java.util.Map;

/**
 * Created by clarence on 2018/3/22.
 */

public class WelcomeObserver extends BaseLifecycleObserver {
    public WelcomeObserver(Context context, Lifecycle lifecycle, Map<String, Object> data) {
        super(context, lifecycle, data);
    }

    @Override
    public void ON_CREATE() {
        super.ON_CREATE();
        LogUtils.getInsatance().debug("LifecycleObserver", "WelcomeObserver", ":ON_CREATE");
    }
}
