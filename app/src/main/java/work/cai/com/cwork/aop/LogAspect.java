package work.cai.com.cwork.aop;

import android.util.Log;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

/**
 * Created by clarence on 2018/1/10.
 */
@Aspect
public class LogAspect {
    private static final String TAG = "ConstructorAspect";

    @Pointcut("call(* work.cai.com.cwork.Animal.fly(..))")
    public void callMethod() {
    }

    @Before("callMethod()")
    public void beforeMethodCall(JoinPoint joinPoint) {
        Log.e(TAG, "before->" + joinPoint.getTarget().toString() + "#" + joinPoint.getSignature().getName());
    }
}
