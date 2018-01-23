package com.cai.framework.store;

import java.util.List;
import java.util.Map;

/**
 * Created by clarence on 2018/1/23.
 */

public interface IStore {
    boolean save(int a);

    boolean save(String a);

    boolean save(Map a);

    boolean save(List a);

    boolean save(float a);

    boolean save(long a);
}
