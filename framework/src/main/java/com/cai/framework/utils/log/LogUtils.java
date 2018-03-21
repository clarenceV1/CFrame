package com.cai.framework.utils.log;

import android.text.TextUtils;

import com.cai.framework.base.BaseConfig;


public class LogUtils implements ILog {

    private boolean isDebug = false;
    private ILog iLog;

    private static class SingletonHolder {
        private static final LogUtils instance = new LogUtils();
    }

    private LogUtils() {
        isDebug = BaseConfig.getInsatance().isDebug();
        iLog = getLog(1);
    }

    private ILog getLog(int type) {
        ILog iLog = null;
        switch (type) {
            case 1:
                iLog = new Log1();
                break;
            default:
                iLog = new Log1();
                break;
        }
        return iLog;
    }

    public static final LogUtils getInsatance() {
        return SingletonHolder.instance;
    }

    public static String getTag(String tag) {
        if (TextUtils.isEmpty(tag)) {
            return "LogUtils";
        }
        return tag;
    }

    public static String getMsg(String... msgs) {
        if (msgs != null && msgs.length > 0) {
            StringBuilder stringBuilder = new StringBuilder();
            for (String msg : msgs) {
                stringBuilder.append(msg);
            }
            return stringBuilder.toString();
        }
        return null;
    }

    @Override
    public void error(String tag, String... msg) {
        if (isDebug) {
            iLog.error(getTag(tag), getMsg(msg));
        }
    }

    @Override
    public void warn(String tag, String... msg) {
        if (isDebug) {
            iLog.warn(getTag(tag), getMsg(msg));
        }
    }

    @Override
    public void info(String tag, String... msg) {
        if (isDebug) {
            iLog.info(getTag(tag), getMsg(msg));
        }
    }

    @Override
    public void debug(String tag, String... msg) {
        if (isDebug) {
            iLog.debug(getTag(tag), getMsg(msg));
        }
    }

    @Override
    public void verbose(String tag, String... msg) {
        if (isDebug) {
            iLog.verbose(getTag(tag), getMsg(msg));
        }
    }

    @Override
    public void showLogPosition(String tag, String... msg) {
        if (isDebug) {
            iLog.showLogPosition(getTag(tag), getMsg(msg));
        }
    }
}
