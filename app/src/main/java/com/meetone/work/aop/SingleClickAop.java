package com.meetone.work.aop;

import android.view.View;

import com.cai.framework.manager.LogDock;
import com.meetone.work.R;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import java.util.Calendar;

/**
 * 防止View被连续点击,间隔时间600ms
 */
@Aspect
public class SingleClickAop {
    private static int TIME_TAG = R.id.click_time;
    public static final int MIN_CLICK_DELAY_TIME = 600;

    @Pointcut("execution(@com.cai.annotation.aspect.SingleClick * *(..))")//方法切入点
    public void methodAnnotated() {
    }

    @Around("methodAnnotated()")//在连接点进行方法替换
    public void aroundJoinPoint(ProceedingJoinPoint joinPoint) throws Throwable {
        View view = null;
        for (Object arg : joinPoint.getArgs()) {
            if (arg instanceof View) {
                view = (View) arg;
            }
        }
        if (view != null) {
            Object tag = view.getTag(TIME_TAG);
            long lastClickTime = ((tag != null) ? (long) tag : 0);
            long currentTime = Calendar.getInstance().getTimeInMillis();
            long clickTime = currentTime - lastClickTime;
            if (clickTime > MIN_CLICK_DELAY_TIME) {//过滤掉600毫秒内的连续点击
                view.setTag(TIME_TAG, currentTime);
                joinPoint.proceed();//执行原方法
                LogDock.getLog().showLogPosition("SingleClickAop", "点击时间差:" + clickTime, "点击生效");
            } else {
                LogDock.getLog().showLogPosition("SingleClickAop", "点击时间差:" + clickTime, "点击不生效");
            }
        }
    }
}
