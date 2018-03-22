package com.cai.work.aop;

import com.example.clarence.utillibrary.log.LogFactory;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import java.util.concurrent.TimeUnit;

/**
 * Created by clarence on 2018/1/11.
 */
@Aspect
public class CostTimeAop {
    @Pointcut("execution(@com.cai.annotation.aspect.CostTime * *(..))")//方法切入点
    public void methodAnnotated() {

    }

    @Pointcut("execution(@com.cai.annotation.aspect.CostTime *.new(..))")//构造器切入点
    public void constructorAnnotated() {
    }

    @Around("methodAnnotated() || constructorAnnotated()")//在连接点进行方法替换
    public Object aroundJoinPoint(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        LogFactory.getInsatance().showLogPosition("CostTime getDeclaringClass", methodSignature.getMethod().getDeclaringClass().getCanonicalName());
        long startTime = System.nanoTime();
        Object result = joinPoint.proceed();//执行原方法
        String className = methodSignature.getDeclaringType().getSimpleName();
        String methodName = methodSignature.getName();
        StringBuilder keyBuilder = new StringBuilder();
        keyBuilder.append(className);
        keyBuilder.append(".");
        keyBuilder.append(methodName + ":");
        for (Object obj : joinPoint.getArgs()) {
            if (obj instanceof String) keyBuilder.append((String) obj);
            else if (obj instanceof Class) keyBuilder.append(((Class) obj).getSimpleName());
        }
        keyBuilder.append(joinPoint.getArgs().toString());
        keyBuilder.append("--->[");
        keyBuilder.append(TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startTime));
        keyBuilder.append("ms]");
        String key = keyBuilder.toString();
        LogFactory.getInsatance().showLogPosition("CostTime", key);// 打印时间差
        return result;
    }
}
