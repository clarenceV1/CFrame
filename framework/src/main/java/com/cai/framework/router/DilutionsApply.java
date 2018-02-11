package com.cai.framework.router;

/**
 * Created by clarence on 2018/2/5.
 */

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.cai.annotation.apt.ActivityExtra;
import com.cai.annotation.apt.ActivityProtocolExtra;
import com.cai.annotation.apt.ActivityProtocolPath;
import com.cai.annotation.apt.FragmentArg;
import com.cai.annotation.apt.ProtocolFrom;
import com.cai.framework.router.handler.ActivityProtocolExtraHandler;
import com.cai.framework.router.handler.ActivityProtocolFromHandler;
import com.cai.framework.router.handler.ActivityProtocolPathHandler;
import com.cai.framework.router.handler.ExtraHandler;
import com.cai.framework.router.handler.FieldHandler;
import com.cai.framework.router.handler.FragmentargHandler;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;

public class DilutionsApply {

    public final ArrayList<FieldHandler> fieldHandlers;

    public DilutionsApply(Builder builder) {
        this.fieldHandlers = builder.fieldHandlers;
    }

    public void apply(Activity activity) throws Exception {
        Intent intent = activity.getIntent();
        if (intent == null) {
            throw new Exception("intent is null");
        }
        Bundle bundle = intent.getExtras();
        if (bundle == null) {
            throw new Exception("bundle is null");
        }
        for (FieldHandler fieldHandler : fieldHandlers) {
            fieldHandler.apply(activity, bundle);
        }
    }

    public void apply(Fragment fragment) throws Exception {
        Bundle bundle = fragment.getArguments();
        if (bundle == null) {
            throw new Exception("bundle is null");
        }
        for (FieldHandler fieldHandler : fieldHandlers) {
            fieldHandler.apply(fragment, bundle);
        }
    }

    static final class Builder {

        //该类的所有具有变量
        private final ArrayList<FieldHandler> fieldHandlers = new ArrayList<>();

        private final Class<?> clazz;

        public Builder(Class<?> clazz) {
            this.clazz = clazz;
        }

        public DilutionsApply build() {
            parseFields();
            return fieldHandlers.size() > 0 ? new DilutionsApply(this) : null;
        }

        private void parseFields() {
            Field[] localfield = clazz.getDeclaredFields();
            for (Field field : localfield) {
                //得到变量的注解
                Annotation[] annotations = field.getDeclaredAnnotations();
                //解析注解
                parseAnnotations(field, annotations);
            }
        }

        private void parseAnnotations(Field field, Annotation[] annotations) {
            for (Annotation annotation : annotations) {
                FieldHandler fieldHandler = parseAnnotation(field, annotation);
                if (fieldHandler == null) {
                    continue;
                }

                //解析成功
                fieldHandlers.add(fieldHandler);
            }
        }

        private FieldHandler parseAnnotation(Field field, Annotation annotation) {
            if (annotation instanceof ActivityExtra) {
                //如果是Activity的注解
                ActivityExtra extra = (ActivityExtra) annotation;
                return new ExtraHandler(field, extra.value());
            } else if (annotation instanceof FragmentArg) {
                //如果是Fragment的注解
                FragmentArg fragmentArg = (FragmentArg) annotation;
                return new FragmentargHandler(field, fragmentArg.value());
            } else if (annotation instanceof ActivityProtocolExtra) {
                //协议注解
                ActivityProtocolExtra activityProtocolExtra = (ActivityProtocolExtra) annotation;
                return new ActivityProtocolExtraHandler(field, activityProtocolExtra.value());
            } else if (annotation instanceof ActivityProtocolPath) {
                //协议
                ActivityProtocolPath activityProtocolPath = (ActivityProtocolPath) annotation;
                return new ActivityProtocolPathHandler(field, activityProtocolPath.value());
            } else if (annotation instanceof ProtocolFrom) {
                //协议
                ProtocolFrom protocolFrom = (ProtocolFrom) annotation;
                return new ActivityProtocolFromHandler(field, protocolFrom.value());
            }
            return null;
        }
    }
}

