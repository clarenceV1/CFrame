package com.cai.framework.router.handler;

import android.os.Bundle;

import com.cai.framework.router.DilutionsInstrument;

import java.lang.reflect.Field;

/**
 * Created by clarence on 2018/2/5.
 */

public class ActivityProtocolFromHandler implements FieldHandler {
    private final Field field;
    private final String name;

    public ActivityProtocolFromHandler(Field field, String name) {
        this.field = field;
        this.name = name;
    }

    @Override
    public void apply(Object object, Bundle bundle) throws Exception {
        //获取传递的path
        String path = bundle.getString(DilutionsInstrument.URI_FROM);
        field.setAccessible(true);
        field.set(object, path);
    }

    @Override
    public String name() {
        return this.name;
    }
}
