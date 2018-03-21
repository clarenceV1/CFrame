package com.cai.framework.watcher;

import android.app.Activity;

import com.cai.framework.utils.log.LogUtils;

import java.lang.ref.WeakReference;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * Created by clarence on 2018/2/5.
 */
class ActivityWatcherImp extends ActivityWatcher {
    private static final String TAG = "Watcher";

    public ActivityWatcherImp() {
    }

    protected static String getActivitySimpleName(WatchParam watchParam) {
        Activity activity = getActivity(watchParam);
        return activity != null ? activity.getClass().getSimpleName() : null;
    }

    protected static Activity getActivity(WatchParam watchParam) {
        if (watchParam != null && watchParam.args != null && watchParam.args.length > 0) {
            WeakReference<Activity> weakReference = (WeakReference) watchParam.args[0];
            if (weakReference != null) {
                return (Activity) weakReference.get();
            }
        }

        return null;
    }

    public void onCreate(WatchParam watchParam) {
        LogUtils.getInsatance().debug(TAG, getActivity(watchParam).getClass().getSimpleName(), " :onCreate");
    }

    public void onStart(WatchParam watchParam) {
        LogUtils.getInsatance().debug(TAG, getActivity(watchParam).getClass().getSimpleName(), " :onStart");
    }

    public void onResume(WatchParam watchParam) {
        LogUtils.getInsatance().debug(TAG, getActivity(watchParam).getClass().getSimpleName(), " :onResume");
    }

    public void onPause(WatchParam watchParam) {
        LogUtils.getInsatance().debug(TAG, getActivity(watchParam).getClass().getSimpleName(), " :onPause");
    }

    public void onStop(WatchParam watchParam) {
        LogUtils.getInsatance().debug(TAG, getActivity(watchParam).getClass().getSimpleName(), " :onStop");
    }

    public void onDestroy(WatchParam watchParam) {
        LogUtils.getInsatance().debug(TAG, getActivity(watchParam).getClass().getSimpleName(), " :onDestroy");
    }

    public void onRestart(WatchParam param) {
    }

    public void onFinish(WatchParam param) {
    }

    public boolean onActivityResult(WatchParam watchParam) {
        LogUtils.getInsatance().debug(TAG, getActivity(watchParam).getClass().getSimpleName() , " :onActivityResult" );
        return true;
    }

    public void onWindowFocusChanged(WatchParam hasFocus) {
    }

    public void onScreenOff() {
    }

    public Map<String, Method> getAllWatchedMethod() {
        return super.getAllWatchedMethod();
    }
}
