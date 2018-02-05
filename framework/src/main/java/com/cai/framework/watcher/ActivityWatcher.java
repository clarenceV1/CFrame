package com.cai.framework.watcher;

/**
 * Created by clarence on 2018/2/5.
 */

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public abstract class ActivityWatcher implements ContextWatcher {
    Map<String, Method> map = new HashMap();

    public ActivityWatcher() {
    }

    public abstract void onCreate(WatchParam var1);

    public abstract void onStart(WatchParam var1);

    public abstract void onResume(WatchParam var1);

    public abstract void onPause(WatchParam var1);

    public abstract void onStop(WatchParam var1);

    public abstract void onDestroy(WatchParam var1);

    public abstract void onRestart(WatchParam var1);

    public abstract void onFinish(WatchParam var1);

    public abstract boolean onActivityResult(WatchParam var1);

    public abstract void onWindowFocusChanged(WatchParam var1);

    public abstract void onScreenOff();

    public Map<String, Method> getAllWatchedMethod() {
        if(!this.map.isEmpty()) {
            return this.map;
        } else {
            Method[] methods = this.getClass().getDeclaredMethods();
            if(methods == null) {
                return null;
            } else {
                for(int i = 0; i < methods.length; ++i) {
                    Method method = methods[i];
                    if(method.getName().startsWith("on")) {
                        this.map.put(method.getName(), method);
                    }
                }

                return this.map;
            }
        }
    }
}

