package com.cai.framework.watcher;

import android.app.Activity;

import com.cai.framework.utils.LogUtils;

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
        LogUtils.d(TAG, getActivity(watchParam).getClass().getSimpleName() + " :onCreate" + new Object[0]);
    }

    public void onStart(WatchParam watchParam) {
        LogUtils.d(TAG, getActivity(watchParam).getClass().getSimpleName() + " :onStart" + new Object[0]);
    }

    public void onResume(WatchParam watchParam) {
        LogUtils.d(TAG, getActivity(watchParam).getClass().getSimpleName() + " :onResume" + new Object[0]);
    }

    public void onPause(WatchParam watchParam) {
        LogUtils.d(TAG, getActivity(watchParam).getClass().getSimpleName() + " :onPause" + new Object[0]);
    }

    public void onStop(WatchParam watchParam) {
        LogUtils.d(TAG, getActivity(watchParam).getClass().getSimpleName() + " :onStop" + new Object[0]);
    }

    public void onDestroy(WatchParam watchParam) {
        LogUtils.d(TAG, getActivity(watchParam).getClass().getSimpleName() + " :onDestroy" + new Object[0]);
    }

    public void onRestart(WatchParam param) {
    }

    public void onFinish(WatchParam param) {
    }

    public boolean onActivityResult(WatchParam watchParam) {
        LogUtils.d(TAG, getActivity(watchParam).getClass().getSimpleName() + " :onActivityResult" + new Object[0]);
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
