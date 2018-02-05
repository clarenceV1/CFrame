package com.cai.framework.watcher;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * Created by clarence on 2018/2/5.
 */

public interface ContextWatcher {
    Map<String, Method> getAllWatchedMethod();
}
