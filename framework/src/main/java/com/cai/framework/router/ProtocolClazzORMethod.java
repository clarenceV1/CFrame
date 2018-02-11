package com.cai.framework.router;

import java.util.ArrayList;

/**
 * Created by clarence on 2018/2/5.
 */

public class ProtocolClazzORMethod {
    public String mPath;
    public Class<?> clazz;
    public String methodName;
    public ArrayList<String> mParamsList;
    public Class<?>[] mClasses;

    public ProtocolClazzORMethod(Class<?> clazz, String methodName, String path, ArrayList<String> paramsList, Class<?>[] classes) {
        this.clazz = clazz;
        this.methodName = methodName;
        this.mPath = path;
        this.mParamsList = paramsList;
        this.mClasses = classes;
    }
}
