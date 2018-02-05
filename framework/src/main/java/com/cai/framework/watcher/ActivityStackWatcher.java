package com.cai.framework.watcher;

import android.app.Activity;

import com.cai.framework.utils.LogUtils;
import com.cai.framework.utils.StringUtils;

import java.lang.ref.WeakReference;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by clarence on 2018/2/5.
 */
public class ActivityStackWatcher extends ActivityWatcherImp {
    protected List<String> activityNameList = new CopyOnWriteArrayList();
    private WeakReference<Activity> mCurrentActivity;

    public ActivityStackWatcher() {
    }

    public WeakReference<Activity> getmCurrentActivity() {
        return this.mCurrentActivity;
    }

    public void onResume(WatchParam watchParam) {
        super.onResume(watchParam);
        Activity activity = getActivity(watchParam);
        this.mCurrentActivity = new WeakReference(activity);
    }

    public void onCreate(WatchParam watchParam) {
        super.onCreate(watchParam);
        String nowName = getActivitySimpleName(watchParam);
        this.activityNameList.add(nowName);
        LogUtils.d("watcher add :" + nowName);
    }

    public void onDestroy(WatchParam watchParam) {
        super.onDestroy(watchParam);
        String nowName = getActivitySimpleName(watchParam);
        Iterator var3 = this.activityNameList.iterator();

        while (var3.hasNext()) {
            String name = (String) var3.next();
            if (StringUtils.equals(name, nowName)) {
                this.activityNameList.remove(name);
                break;
            }
        }

    }

    public void onRestart(WatchParam param) {
        super.onRestart(param);
    }

    public String getCurrentActivityName() {
        return this.activityNameList.size() == 0 ? "" : (String) this.activityNameList.get(this.activityNameList.size() - 1);
    }

    public String getPreActivityName() {
        return this.activityNameList.size() >= 2 ? (String) this.activityNameList.get(this.activityNameList.size() - 2) : "";
    }
}

