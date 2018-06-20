package com.cai.work.aop;

import android.text.TextUtils;

import com.cai.framework.MemoryCacheManager;
import com.cai.framework.manager.LogDock;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import java.util.List;

/**
 * 根据MemoryCache注解自动添加缓存代理代码，通过aop切片的方式在编译期间织入源代码中
 * 功能：缓存某方法的返回值，下次执行该方法时，直接从缓存里获取。
 */
@Aspect
public class MemoryCacheAop {

    @Pointcut("execution(@com.cai.annotation.aspect.MemoryCache * *(..))")//方法切入点
    public void methodAnnotated() {
    }

    @Around("methodAnnotated()")//在连接点进行方法替换
    public Object aroundJoinPoint(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        String methodName = methodSignature.getName();
        MemoryCacheManager mMemoryCacheManager = MemoryCacheManager.getInstance();
        StringBuilder keyBuilder = new StringBuilder();
        keyBuilder.append(methodName);
        for (Object obj : joinPoint.getArgs()) {
            if (obj instanceof String) keyBuilder.append((String) obj);
            else if (obj instanceof Class) keyBuilder.append(((Class) obj).getSimpleName());
        }
        String key = keyBuilder.toString();
        Object result = mMemoryCacheManager.get(key);//key规则 ： 方法名＋参数1+参数2+...
        if (result != null) {
            LogDock.getLog().showLogPosition("MemoryCache", "key：" , key.substring(6) , "对象----->已存在用缓存");
            return result;//缓存已有，直接返回
        }
        result = joinPoint.proceed();//执行原方法
        LogDock.getLog().showLogPosition("MemoryCache", "key：" + key.substring(6) + "对象----->不存在开始创建...");
        if (result instanceof List && result != null && ((List) result).size() > 0 //列表不为空
                || result instanceof String && !TextUtils.isEmpty((String) result)//字符不为空
                || result instanceof Object && result != null)//对象不为空
            mMemoryCacheManager.add(key, result);//存入缓存
        LogDock.getLog().showLogPosition("MemoryCache", "key：" , key.substring(6) , "对象----->缓存成功");

        return result;
    }
}
