package com.cai.framework.router.handler;

import android.os.Bundle;

/**
 * Created by clarence on 2018/2/5.
 */

public interface FieldHandler {
    void apply(Object object, Bundle bundle) throws Exception;

    String name();
}
