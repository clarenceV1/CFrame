package com.cai.framework.router.handler;

import android.os.Bundle;

import java.lang.reflect.Field;

/**
 * Created by clarence on 2018/2/5.
 */

public class ExtraHandler implements FieldHandler {
    private final Field field;
    private final String name;

    public ExtraHandler(Field field, String name) {
        this.field = field;
        this.name = name;
    }

    @Override
    public void apply(Object object, Bundle bundle) throws Exception {
        Object key = bundle.get(name);
        if (key != null) {
            field.setAccessible(true);
            field.set(object, key);
        }
    }

    @Override
    public String name() {
        return this.name;
    }
}
