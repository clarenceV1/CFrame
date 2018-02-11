package com.cai.framework.router.handler;

import android.os.Bundle;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cai.framework.router.DilutionsInstrument;
import com.cai.framework.router.DilutionsUtil;
import com.cai.framework.router.DilutionsValue;

import java.lang.reflect.Field;

/**
 * Created by clarence on 2018/2/5.
 */

public class FragmentargHandler implements FieldHandler {
    private final Field field;
    private final String name;

    public FragmentargHandler(Field field, String name) {
        this.field = field;
        this.name = name;
    }

    @Override
    public void apply(Object object, Bundle bundle) throws Exception {
        String jsonParam = bundle.getString(DilutionsInstrument.URI_CALL_PARAM);
        if (DilutionsUtil.isNull(jsonParam)) {
            //没有找到json
            Object key = bundle.get(name);
            if (key != null) {
                field.setAccessible(true);
                field.set(object, key);
            }
            return;
        }
        JSONObject jsonObject = JSON.parseObject(jsonParam);
        if (jsonObject == null) {
            //转换失败
            return;
        }
        jsonObject = jsonObject.getJSONObject(DilutionsValue.VAL_PARAMS);
        if (jsonObject == null) {
            return;
        }
        Object key = jsonObject.get(name);

        if (key != null) {
            if (key instanceof JSONObject) {
                //转换为对象
                key = JSON.parseObject(jsonObject.get(name).toString(), field.getType());
            }
            field.setAccessible(true);
            field.set(object, key);
        }
    }

    @Override
    public String name() {
        return this.name;
    }
}
