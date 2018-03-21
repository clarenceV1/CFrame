package com.cai.framework.watcher;

/**
 * Created by clarence on 2018/2/5.
 */

import com.cai.framework.utils.log.LogUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class WatcherManager {
    Map<String, ActivityWatcher> contextWatcherMap = new HashMap();

    public WatcherManager() {
    }

    public static WatcherManager getInstance() {
        return WatcherManager.Holder.instance;
    }

    /**
     * @deprecated
     */
    @Deprecated
    public WatcherManager addWatcher(String key, ActivityWatcher watcher) {
        LogUtils.getInsatance().debug("add watcher key " + key,"");
        if (watcher == null) {
            return this;
        } else {
            if (this.contextWatcherMap.containsKey(key)) {
                LogUtils.getInsatance().error("WatcherManager", "出现重复值了!!!!!!!!!!!!!会出Bug,请检查代码");
            }

            this.contextWatcherMap.put(key, watcher);
            return this;
        }
    }

    /**
     * @deprecated
     */
    @Deprecated
    public WatcherManager addWatcher(String key, String classname) {
        try {
            if (this.contextWatcherMap.containsKey(key)) {
                LogUtils.getInsatance().error("WatcherManager", "出现重复值了!!!!!!!!!!!!!会出Bug,请检查代码" + new Object[0]);
            }
            LogUtils.getInsatance().debug("add watcher classname " + key);
            Class<?> clas = Class.forName(classname);
            Object assetMag = clas.newInstance();
            ActivityWatcher watcher = (ActivityWatcher) assetMag;
            this.contextWatcherMap.put(key, watcher);
        } catch (Exception var6) {
            var6.printStackTrace();
        }

        return this;
    }

    /**
     * @deprecated
     */
    @Deprecated
    public ContextWatcher getWatcher(String key) {
        return (ContextWatcher) this.contextWatcherMap.get(key);
    }

    /**
     * @deprecated
     */
    @Deprecated
    public void removeWatcher(String key) {
        this.contextWatcherMap.remove(key);
    }

    /**
     * @deprecated
     */
    @Deprecated
    public void onFired(String methodName, Object[] args) {
        try {
            WatchParam param = new WatchParam(args, methodName);
            Iterator var4 = this.contextWatcherMap.values().iterator();

            while (var4.hasNext()) {
                ActivityWatcher source = (ActivityWatcher) var4.next();
                Method method = source.getAllWatchedMethod() == null ? null : (Method) source.getAllWatchedMethod().get(methodName);
                if (method != null) {
                    method.invoke(source, new Object[]{param});
                }
            }
        } catch (IllegalAccessException var7) {
            LogUtils.getInsatance().error(var7.getLocalizedMessage());
        } catch (InvocationTargetException var8) {
            ;
        }

    }

    public void onCreate(Object[] args) {
        WatchParam param = new WatchParam(args, "onCreate");
        Iterator var3 = this.contextWatcherMap.values().iterator();

        while (var3.hasNext()) {
            ActivityWatcher source = (ActivityWatcher) var3.next();

            try {
                source.onCreate(param);
            } catch (Exception var6) {
                var6.printStackTrace();
            }
        }

    }

    public void onStart(Object[] args) {
        WatchParam param = new WatchParam(args, "onStart");
        Iterator var3 = this.contextWatcherMap.values().iterator();

        while (var3.hasNext()) {
            ActivityWatcher source = (ActivityWatcher) var3.next();

            try {
                source.onStart(param);
            } catch (Exception var6) {
                var6.printStackTrace();
            }
        }

    }

    public void onResume(Object[] args) {
        WatchParam param = new WatchParam(args, "onResume");
        Iterator var3 = this.contextWatcherMap.values().iterator();

        while (var3.hasNext()) {
            ActivityWatcher source = (ActivityWatcher) var3.next();

            try {
                source.onResume(param);
            } catch (Exception var6) {
                var6.printStackTrace();
            }
        }

    }

    public void onPause(Object[] args) {
        WatchParam param = new WatchParam(args, "onPause");
        Iterator var3 = this.contextWatcherMap.values().iterator();

        while (var3.hasNext()) {
            ActivityWatcher source = (ActivityWatcher) var3.next();

            try {
                source.onPause(param);
            } catch (Exception var6) {
                var6.printStackTrace();
            }
        }

    }

    public void onStop(Object[] args) {
        WatchParam param = new WatchParam(args, "onStop");
        Iterator var3 = this.contextWatcherMap.values().iterator();

        while (var3.hasNext()) {
            ActivityWatcher source = (ActivityWatcher) var3.next();

            try {
                source.onStop(param);
            } catch (Exception var6) {
                var6.printStackTrace();
            }
        }

    }

    public void onDestroy(Object[] args) {
        WatchParam param = new WatchParam(args, "onDestroy");
        Iterator var3 = this.contextWatcherMap.values().iterator();

        while (var3.hasNext()) {
            ActivityWatcher source = (ActivityWatcher) var3.next();

            try {
                source.onDestroy(param);
            } catch (Exception var6) {
                var6.printStackTrace();
            }
        }

    }

    public void onRestart(Object[] args) {
        WatchParam param = new WatchParam(args, "onRestart");
        Iterator var3 = this.contextWatcherMap.values().iterator();

        while (var3.hasNext()) {
            ActivityWatcher source = (ActivityWatcher) var3.next();

            try {
                source.onRestart(param);
            } catch (Exception var6) {
                var6.printStackTrace();
            }
        }

    }

    public void onFinish(Object[] args) {
        WatchParam param = new WatchParam(args, "onFinish");
        Iterator var3 = this.contextWatcherMap.values().iterator();

        while (var3.hasNext()) {
            ActivityWatcher source = (ActivityWatcher) var3.next();

            try {
                source.onFinish(param);
            } catch (Exception var6) {
                var6.printStackTrace();
            }
        }

    }

    public boolean onActivityResult(Object[] args) {
        WatchParam param = new WatchParam(args, "onActivityResult");
        Iterator var3 = this.contextWatcherMap.values().iterator();

        while (var3.hasNext()) {
            ActivityWatcher source = (ActivityWatcher) var3.next();

            try {
                source.onActivityResult(param);
            } catch (Exception var6) {
                var6.printStackTrace();
            }
        }

        return true;
    }

    public void onWindowFocusChanged(Object[] args) {
        WatchParam param = new WatchParam(args, "onWindowFocusChanged");
        Iterator var3 = this.contextWatcherMap.values().iterator();

        while (var3.hasNext()) {
            ActivityWatcher source = (ActivityWatcher) var3.next();

            try {
                source.onWindowFocusChanged(param);
            } catch (Exception var6) {
                var6.printStackTrace();
            }
        }

    }

    public void onScreenOff() {
        Iterator var1 = this.contextWatcherMap.values().iterator();

        while (var1.hasNext()) {
            ActivityWatcher source = (ActivityWatcher) var1.next();

            try {
                source.onScreenOff();
            } catch (Exception var4) {
                var4.printStackTrace();
            }
        }

    }

    static class Holder {
        static WatcherManager instance = new WatcherManager();

        Holder() {
        }
    }
}

