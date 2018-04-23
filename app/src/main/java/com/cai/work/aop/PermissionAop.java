package com.cai.work.aop;

import android.content.Context;
import android.support.v4.app.FragmentActivity;

import com.cai.annotation.aspect.Permission;
import com.cai.framework.utils.PermissionUtils;
import com.cai.work.base.App;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import java.util.List;

/**
 * 申请系统权限切片，根据注解值申请所需运行权限
 */
@Aspect
public class PermissionAop {

    @Around("execution(@com.cai.annotation.aspect.Permission * *(..)) && @annotation(permission)")
    public void aroundJoinPoint(final ProceedingJoinPoint joinPoint, final Permission permission) throws Throwable {
        final Context context = App.getAppContext();
        List<String> deniedPermissionList = PermissionUtils.getDeniedPermissions(context, permission.value());
        if (deniedPermissionList.isEmpty()) {
            joinPoint.proceed();//获得权限，执行原方法
            return;
        }
        String[] deniedPermissions = deniedPermissionList.toArray(new String[deniedPermissionList.size()]);
        FragmentActivity ac = (FragmentActivity) App.getAppContext().getCurrentActivity();
        if (ac != null) {
            PermissionUtils.requestPermissionsResult(ac, 1, deniedPermissions
                    , new PermissionUtils.OnPermissionListener() {
                        @Override
                        public void onPermissionGranted() {
                            try {
                                joinPoint.proceed();//获得权限，执行原方法
                            } catch (Throwable e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onPermissionDenied() {
                            PermissionUtils.showTipsDialog(context);
                        }
                    });
        }
    }
}


