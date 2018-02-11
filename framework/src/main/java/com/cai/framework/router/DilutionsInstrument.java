package com.cai.framework.router;

/**
 * Created by clarence on 2018/2/5.
 */

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;


/**
 * Created by Linhh on 16/9/2.
 */
public class DilutionsInstrument {
    public static final String TAG = "Dilutions_info";

    public static final String URI_CALL_CLASS = "uri-call-clazz";
    public static final String URI_CALL_PATH = "uri-call-path";
    public static final String URI_CALL_PARAM = "uri-call-param";
    public static final String URI_CALL_ALL = "uri-call-all";
    public static final String URI_FROM = "uri-from";
    public static final String SCHEME_IN = "DILUTIONS.SCHEME.IN";
    public static final String SCHEME_OUT = "DILUTIONS.SCHEME.OUT";

    public static final String PATH_JUMP_FILE = "uiInterpreter.conf";

    private DilutionsGlobalListener mDilutionsGlobalListener;

    public static final int PARSER_TYPE_JUMP = 0;
    public static final int PARSER_TYPE_METHOD = 1;

    private final Map<Class<?>, DilutionsApply> managerCache = new LinkedHashMap<>();

    //方法缓存
    private final Map<Method, ProtocolManager> protocolCache = new LinkedHashMap<>();
    private final Map<String, ProtocolClazzORMethod> protocolClazzORMethodMap = new HashMap<>();

    private Map<String, ArrayList<String>> jumpPathMap;//pathMap, key = 协议/my/info, value = com.activity
    private final Map<String, Map<String, String>> jumpParamsMap = new HashMap<>();//paramsMap key = 协议/my/info , value = 参数以及检查类型

    private HashMap<String, ArrayList<String>> methodPathMap;//pathMap, key = 协议/my/info, value = com.activity
//    private final Map<String, Map<String, String>> methodParamsMap = new HashMap<>();//paramsMap key = 协议/my/info , value = 参数以及检查类型

    private final Map<String, Class<?>> checkMap = new HashMap<>();//参数合法性检查
    private final List<String> appMap = new ArrayList<>();//协议头map
    private final HashMap<String, ArrayList<DilutionsPathInterceptor>> mDilutionsPathInterceptor = new HashMap<>();
//    private final List<String> appOutMap = new ArrayList<>();//协议外

    private Context mContext;
    private List<String> mPathName;

    DilutionsInstrument(Context context, List<String> pathName) {
        mContext = context;
        mPathName = pathName;
    }

    void removeDilutionsPathInterceptor(String path, DilutionsPathInterceptor interceptor) {
        ArrayList<DilutionsPathInterceptor> interceptors = mDilutionsPathInterceptor.get(path);
        if (interceptors != null) {
            interceptors.remove(interceptor);
        }
    }

    void setDilutionsPathInterceptor(String path, DilutionsPathInterceptor dilutionsPathInterceptor) {
        ArrayList<DilutionsPathInterceptor> interceptors = mDilutionsPathInterceptor.get(path);
        if (interceptors == null) {
            interceptors = new ArrayList<>();
        }
        if (interceptors.size() == 0) {
            interceptors.add(dilutionsPathInterceptor);
            mDilutionsPathInterceptor.put(path, interceptors);
            return;
        }
        for (int i = 0; i < interceptors.size(); i++) {
            DilutionsPathInterceptor interceptor = interceptors.get(i);
            if (interceptor.level() == dilutionsPathInterceptor.level()) {
//                Log.e(TAG, "path:" + path + ",拦截器添加失败、已经存在" + interceptor.getClass() + ",级别:" + dilutionsPathInterceptor.level());
                return;
            }
            if (interceptor.level() < dilutionsPathInterceptor.level()) {
                interceptors.add(i, dilutionsPathInterceptor);
                mDilutionsPathInterceptor.put(path, interceptors);
                return;
            }
        }
        interceptors.add(dilutionsPathInterceptor);
        mDilutionsPathInterceptor.put(path, interceptors);

    }

    void setDilutionsGlobalListener(DilutionsGlobalListener dilutionsGlobalListener) {
        if (mDilutionsGlobalListener != null) {
//            Log.e(TAG, "DilutionsGlobalListener is already exists.");
            return;
        }
        mDilutionsGlobalListener = dilutionsGlobalListener;
    }

    DilutionsGlobalListener getDilutionsGlobalListener() {
        return mDilutionsGlobalListener;
    }

    /**
     * 初始化
     */
    public void init() throws Exception {
        initUIProtocol();
        initMethodProtocol();
        if (jumpPathMap == null) {
            jumpPathMap = new HashMap<>();
        }
        if (methodPathMap == null) {
            methodPathMap = new HashMap<>();
        }
        initCheckMap();
        //解析path
        if (mPathName != null) {
            for (String pathname : mPathName) {
                parserData(pathname, PARSER_TYPE_JUMP);
            }
        }
    }

    public Map<String, ArrayList<String>> getJumpPathMap() {
        return jumpPathMap;
    }

    public HashMap<String, ArrayList<String>> getMethodPathMap() {
        return methodPathMap;
    }

    /**
     * 初始化数据类型处理器
     */
    private void initCheckMap() {
        checkMap.put("int", Integer.class);
        checkMap.put("String", String.class);
        checkMap.put("long", Long.class);
        checkMap.put("double", Double.class);
        checkMap.put("boolean", Boolean.class);
        checkMap.put("float", Float.class);
    }

    /**
     * 初始化方法协议
     */
    private void initMethodProtocol() {
        try {
            Class methodMapClazz = Class.forName("com.meetyou.dilutions.inject.support.DilutionsInjectMetas");
            Object obj = methodMapClazz.newInstance();

            Method method = methodMapClazz.getMethod("getMap");
            methodPathMap = (HashMap<String, ArrayList<String>>) method.invoke(obj);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 初始化UI协议
     */
    private void initUIProtocol() throws Exception {
        try {
            Class methodMapClazz = Class.forName("com.meetyou.dilutions.inject.support.DilutionsInjectUIMetas");
            Object obj = methodMapClazz.newInstance();

            Method method = methodMapClazz.getMethod("getMap");
            jumpPathMap = (HashMap<String, ArrayList<String>>) method.invoke(obj);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Map<String, Class<?>> getCheckMap() {
        return checkMap;
    }

    /**
     * 获得appmap
     *
     * @return
     */
    public List<String> getAppMap() {
        return appMap;
    }
    /**
     * 设置http协议参数
     * @param jsonObject
     * @return
     */
    public void createExtraParams(ArrayList<ParameterHanlder<?>> parameterHandlers, String path, JSONObject jsonObject) throws Exception{
        //2017.7.6修改,3.0特殊处理,如果jsonobJECT为空,不抛出错误
        if(jsonObject == null ){
            jsonObject = new JSONObject();
        }
        if(DilutionsUtil.isNull(path)){
            //JSON为空,无法解析
            throw new Exception("path is null");
        }
        //获得参数以及其类型,限制性做法
        for(Map.Entry<String, Object> entry : jsonObject.entrySet()){
            String key = entry.getKey();
//            Class<?> clazz = getParamsType(path, key);
//            if(clazz == null){
//                //没有配置转换类型
//                continue;
//            }
            Object value = jsonObject.get(key);
            if(value != null) {
                Class<?> clazz = value.getClass();
                ParameterHanlder.ExtraParams parameterHandler = new ParameterHanlder.ExtraParams<>(key, clazz.cast(value));
                parameterHandler.setType(clazz);
                parameterHandlers.add(parameterHandler);
            }else{
                //解析空值null
            }
        }
    }

    /**
     * 旧的协议配置解析
     * 解析数据
     * @param name
     * @throws Exception
     */
    private void parserData(String name, int type) throws Exception {
        InputStream pathis = getContext().getAssets().open(name);
        Properties properties = new Properties();
        properties.load(pathis);
        Enumeration<Object> keys = properties.keys();
        while (keys.hasMoreElements()) {
            String key = (String) keys.nextElement();//协议
            String value = properties.getProperty(key).trim();
            if (DilutionsUtil.isEqual(SCHEME_IN, key)) {
                //配置头
                parseScheme(appMap, value);
            } else {
                parseQuery(key, value, jumpPathMap);
            }
        }
    }

    /**
     * 解析参数
     *
     * @param key
     * @param value
     * @return true 解析成功,false失败
     */
    private boolean parseQuery(String key, String value, Map<String, ArrayList<String>> pathMap) {
        String[] sp = value.split("\\(");//窃取数据
        String path = sp[0].trim();
        ArrayList<String> list = new ArrayList<>();
        list.add(path);
        pathMap.put(key, list);//将跳转位置加入path, class类
        return true;
    }

    public void parseScheme(List<String> map, String value) {
        String[] params = value.split(",");
        for (String param : params) {
            String in = param.trim().substring(1, param.length() - 1);
            if (!map.contains(in)) {
                map.add(in);
            }
        }
    }

    /**
     * 检查数据合法性
     */
    boolean checkJumpUriSafe(String scheme, String path) {
//        return jumpPathMap.containsKey(path);
//        if (scheme.contains(APP_SCHEME)) {
//            return jumpPathMap.containsKey(path);
//        }
        for (int i = 0; i < appMap.size(); ++i) {
            if (scheme.contains(appMap.get(i))) {
                return jumpPathMap.containsKey(path);
            }
        }
        return false;
//        return pathMap.containsKey(path);
    }

    public int getUriType(String path) throws Exception {
        if (jumpPathMap.containsKey(path)) {
            return DilutionsValue.PROTOCOL_JUMP;
        } else if (methodPathMap.containsKey(path)) {
            return DilutionsValue.PROTOCOL_METHOD;
        }
        throw new Exception("Uri协议出错,不存在[" + path + "]协议");
    }

    public int getUriType(String scheme, String path) throws Exception {
        if (checkJumpUriSafe(scheme, path)) {
            return DilutionsValue.PROTOCOL_JUMP;
        } else if (checkMethodUriSafe(scheme, path)) {
            return DilutionsValue.PROTOCOL_METHOD;
        }
        throw new Exception("Uri协议出错,不存在[" + scheme + "][" + path + "]协议");
    }

    /**
     * 检查方法数据合法性
     */
    boolean checkMethodUriSafe(String scheme, String path) {
//        return methodPathMap.containsKey(path);
//        if (scheme.contains(APP_SCHEME)) {
//            return methodPathMap.containsKey(path);
//        }
        for (int i = 0; i < appMap.size(); ++i) {
            if (scheme.contains(appMap.get(i))) {
                return methodPathMap.containsKey(path);
            }
        }
        return false;
//        return pathMap.containsKey(path);
    }

    /**
     * 注册稀释器
     */
    public void register(Object object) {
        Class<?> clazz = object.getClass();
        if (object instanceof Activity) {
            //是Activity
            registerActivity(clazz, object);
        } else if (object instanceof Fragment) {
            //是fragment
            registerFragment(clazz, object);
        } else {
            //无法识别
            return;
        }
    }

    /**
     * 注册Fragment
     *
     * @param clazz
     * @param object
     */
    private void registerFragment(Class<?> clazz, Object object) {
        //得到当前Fragment的所有对象
        DilutionsApply result = getManager(clazz);
        try {
            if (result != null) {
                result.apply((Fragment) object);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 注册Activity
     *
     * @param clazz
     * @param object
     */
    private void registerActivity(Class<?> clazz, Object object) {
        //得到当前Activity的所有对象
        DilutionsApply result = getManager(clazz);
        try {
            if (result != null) {
                result.apply((Activity) object);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private DilutionsApply getManager(Class<?> clazz) {
        //得到当前Activity的所有对象
        DilutionsApply result;
        //不要使用缓存，因为所在的args是不同的
        synchronized (managerCache) {
            result = managerCache.get(clazz);
            if (result == null) {
                result = new DilutionsApply.Builder(clazz).build();
                managerCache.put(clazz, result);
            }
        }
        return result;
    }

    /**
     * 通过path获取需要跳转的class
     *
     * @param path
     * @return
     */
    public ProtocolClazzORMethod getClazz(int protocolType, String path) throws Exception {
        if (protocolClazzORMethodMap.containsKey(path)) {
            return protocolClazzORMethodMap.get(path);
        }
        ProtocolClazzORMethod protocolClazzORMethod = null;
        //只会被调用一次
        switch (protocolType) {
            case DilutionsValue.PROTOCOL_JUMP:
                Class<?> jumpClazz = Class.forName(jumpPathMap.get(path).get(0));
                protocolClazzORMethod = new ProtocolClazzORMethod(jumpClazz, null, path, null, null);
                protocolClazzORMethodMap.put(path, protocolClazzORMethod);
                return protocolClazzORMethod;
            case DilutionsValue.PROTOCOL_METHOD:
                //返回方法跳转类和方法名sp[0]=类,sp[1]=方法
                Class<?> methodClazz = Class.forName(methodPathMap.get(path).get(0));
                //取出类参数类型
                String j = methodPathMap.get(path).get(2);
                String[] param_type = null;
                if (j == null || j.length() == 0) {
                    param_type = new String[]{};
                } else {
                    param_type = j.split("#");
                }
                Class<?>[] classes = new Class[param_type.length];
                for (int i = 0; i < param_type.length; i++) {
                    classes[i] = DilutionsUtil.getParamsClass(param_type[i]);
                }
                protocolClazzORMethod = new ProtocolClazzORMethod(methodClazz, methodPathMap.get(path).get(1), path, methodPathMap.get(path), classes);
                protocolClazzORMethodMap.put(path, protocolClazzORMethod);
                return protocolClazzORMethod;
            default:
                throw new Exception("协议错误");
        }

    }

    public HttpProtocolManager createHttpManager(Uri uri, HashMap<String, Object> map) throws Exception{
        return new HttpProtocolManager.Builder(this, uri, map).build();
    }
    /**
     * 处理协议
     *
     * @param protocolManager
     */
    void dilutions(DilutionsManager protocolManager, DilutionsConfig config) throws Exception {
        protocolManager.apply();
        DilutionsData data = protocolManager.getDilutionsBuilder().getDilutionsData();
        DilutionsCallBack callBack = null;
        DilutionsInterceptor interceptor = null;
        String path = protocolManager.getDilutionsBuilder().getPath();
        String u = "";
        if (data.getUri() != null) {
            u = data.getUri().toString();
        }
//        Log.e(TAG, "path:" + path + "协议开始执行。" + u);
        if (mDilutionsGlobalListener != null && mDilutionsGlobalListener.onIntercept(data)) {
//            Log.e(TAG, "path:" + path + "被拦截,拦截者_全局拦截:" + interceptor.getClass());
            return;
        }
        //执行协议拦截器
        ArrayList<DilutionsPathInterceptor> interceptors = mDilutionsPathInterceptor.get(path);
        if (interceptors != null && interceptors.size() > 0) {

            for (DilutionsPathInterceptor interceptor1 : interceptors) {
                if (interceptor1.interceptor(data)) {
//                    Log.e(TAG, "path:" + path + "被局部拦截,拦截者:" + interceptor1.getClass() + ",level:" + interceptor1.level());
                    return;
                }
            }
        }
        //处理协议
//        Log.d("dilutions", "Process:" + path);
        if (config != null) {
            callBack = config.getDilutionsCallBack();
            interceptor = config.getDilutionsInterceptor();
            data.setWhat(config.getWhat());
        }

        if (interceptor != null && interceptor.interceptor(data)) {
//            Log.e(TAG, "path:" + path + "被拦截,拦截者_发起协议者:" + interceptor.getClass());
            //拦截
            return;
        }
//        try {
        //TODO:捕获的原因是因为想让callback继续执行
        //处理协议最后逻辑
        if (protocolManager.getProtocolType() == DilutionsValue.PROTOCOL_JUMP) {
            mContext.startActivity(data.getIntent());
        } else if (protocolManager.getProtocolType() == DilutionsValue.PROTOCOL_METHOD) {
            //方法解析
//                if(!methodMap.containsKey(path)) {
            //缓存处理
            Class<?> clazz = protocolManager.getProtocolClazzORMethod().clazz;
            String methodName = protocolManager.getProtocolClazzORMethod().methodName;

            Object obj = protocolManager.getProtocolClazzORMethod().clazz.newInstance();

            //取出类参数类型
            String j = protocolManager.getProtocolClazzORMethod().mParamsList.get(2);
//                    String[] param_type = j.split("#");
//                    Class<?>[] classes = new Class[param_type.length];
//                    for(int i = 0 ;i < param_type.length; i++){
//                        classes[i] = DilutionsUtil.getParamsClass(param_type[i]);
//                    }

            Method method = clazz.getDeclaredMethod(methodName, protocolManager.getProtocolClazzORMethod().mClasses);
            if (j.equals("")) {
                //空参数
                method.invoke(obj);
            } else {
                //需要缓存
                HashMap<Integer, String> indexs = new HashMap<>();
                for (int i = 3; i < protocolManager.getProtocolClazzORMethod().mParamsList.size(); i++) {
                    String[] s = protocolManager.getProtocolClazzORMethod().mParamsList.get(i).split("=");
                    indexs.put(Integer.valueOf(s[0]), s[1]);
                }
                String[] params_type = j.split("#");
                Object[] objs = new Object[params_type.length];
                Bundle bundle = data.getIntent().getExtras();
                for (int i = 0; i < params_type.length; i++) {

                    if (indexs.containsKey(i)) {
                        //存在字段
                        Class type = protocolManager.getProtocolClazzORMethod().mClasses[i];
                        Object object = bundle.get(indexs.get(i));
                        if (object == null) {
                            //没有该参数
                            object = DilutionsUtil.getParams(params_type[i]);
                        } else {
                            if (type == String.class) {
                                object = String.valueOf(object);
                            } else if (type == Integer.class) {
                                object = Integer.valueOf(String.valueOf(object));
                            } else if (type == int.class) {
                                object = Integer.valueOf(String.valueOf(object)).intValue();
                            } else if (type == Long.class) {
                                object = Long.valueOf(String.valueOf(object));
                            } else if (type == long.class) {
                                object = Long.valueOf(String.valueOf(object)).longValue();
                            } else if (type == Double.class) {
                                object = Double.valueOf(String.valueOf(object));
                            } else if (type == double.class) {
                                object = Double.valueOf(String.valueOf(object)).doubleValue();
                            } else if (type == Boolean.class) {
                                object = Boolean.valueOf(String.valueOf(object));
                            } else if (type == boolean.class) {
                                object = Boolean.valueOf(String.valueOf(object)).booleanValue();
                            } else if (type == Float.class) {
                                object = Float.valueOf(String.valueOf(object));
                            } else if (type == float.class) {
                                object = Float.valueOf(String.valueOf(object)).floatValue();
                            } else {
                                if (object instanceof JSON) {
                                    //是json
                                    object = ((JSON) object).toJavaObject(protocolManager.getProtocolClazzORMethod().mClasses[i]);
                                }
                            }
                        }
                        objs[i] = object;

                    } else {
                        objs[i] = DilutionsUtil.getParams(params_type[i]);
                    }
                }
                Object object = method.invoke(obj, objs);
                data.setResult(object);
            }

        }


        //callback
        if (callBack != null) {
            callBack.onDilutions(data);
        }

        if (mDilutionsGlobalListener != null) {
            mDilutionsGlobalListener.onCallBack(data);
        }

    }

    /**
     * 获得builder
     *
     * @return
     */
    DilutionsBuilder getDilutionsBuilder() {
        return new DilutionsBuilder(mContext);
    }

    /**
     * 获得context
     *
     * @return
     */
    public Context getContext() {
        return mContext;
    }
}

