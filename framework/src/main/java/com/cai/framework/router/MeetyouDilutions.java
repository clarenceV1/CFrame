package com.cai.framework.router;

/**
 * Created by clarence on 2018/2/5.
 */

import android.content.Context;
import android.net.Uri;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MeetyouDilutions {

    private static DilutionsInstrument dilutionsInstrument;
    private static MeetyouDilutions meetyouDilutions;

    private MeetyouDilutions() {

    }

    public static void init(Context context) {
        init(context, DilutionsInstrument.PATH_JUMP_FILE);
    }

    /**
     * 初始化,需要在application内初始化
     *
     * @param context
     */
    public static void init(Context context, String pathName) {
        if (meetyouDilutions == null) {
            List<String> path_list = new ArrayList<>();
            if (!DilutionsUtil.isNull(pathName)) {
                path_list.add(pathName);
            }
            meetyouDilutions = new MeetyouDilutions();
            dilutionsInstrument = new DilutionsInstrument(context, path_list);
            try {
                dilutionsInstrument.init();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static MeetyouDilutions create() {
        if (meetyouDilutions == null) {
            new Throwable("com.cai.framework.router.MeetyouDilutions.init(android.content.Context) is null");
            return null;
        }
        return meetyouDilutions;
    }
    /**
     * 注册Activity
     */
    public void register(Object object) {
        dilutionsInstrument.register(object);
    }

    /**
     * Activity的newIntent，暂时使用register
     *
     * @param object
     */
    public void onNewIntent(Object object) {
        dilutionsInstrument.register(object);
    }

    /**
     * 处理HTtp协议
     * @param uri_string
     */
    public boolean formatProtocolService(final String uri_string){
        return formatProtocolService(uri_string, null);
    }

    /**
     * 自定义协议发起
     * @param scheme
     * @param path
     * @param query_json
     * @return
     */
    public boolean formatProtocolService(final String scheme, final String path, final String query_json){
        return formatProtocolService(DilutionsUriBuilder.buildUri(scheme, path, query_json));
    }

    public boolean formatProtocolService(final String scheme, final String path, final JSONObject query_json){
        return formatProtocolService(DilutionsUriBuilder.buildUri(scheme, path, query_json));
    }

    public boolean formatProtocolService(final String scheme, final String path, final org.json.JSONObject query_json){
        return formatProtocolService(scheme, path, query_json.toString());
    }

    public <K,V> boolean  formatProtocolService(final String scheme, final String path, final Map<K,V> map){
        String json = JSON.toJSONString(map);
        return formatProtocolService(DilutionsUriBuilder.buildUri(scheme, path, json));
    }
    /**
     * 带有监听的协议解析
     * @param uri_string
     * @param config
     */
    public boolean formatProtocolService(final String uri_string, final DilutionsConfig config){
        return formatProtocolService(uri_string, null,  config);
    }

    public boolean formatProtocolServiceWithCallback(final String uri_string, final DilutionsCallBack callBack){
        return formatProtocolService(uri_string, null, DilutionsConfigFactory.newBuilder(callBack, null, null).build());
    }

    public boolean formatProtocolServiceWithInterceptor(final String uri_string, final DilutionsInterceptor interceptor){
        return formatProtocolService(uri_string, null, DilutionsConfigFactory.newBuilder(null, interceptor, null).build());
    }

    public Map<String, ArrayList<String>> getJumpPathMap(){
        return dilutionsInstrument.getJumpPathMap();
    }

    public HashMap<String, ArrayList<String>> getMethodPathMap(){
        return dilutionsInstrument.getMethodPathMap();
    }
    /**
     * 带有监听的http协议处理
     * @param uri
     * @param config
     */
    public boolean formatProtocolService(final String uri, final HashMap<String, Object> map, final DilutionsConfig config){
        try {
            DilutionsManager httpProtocolManager = dilutionsInstrument.createHttpManager(Uri.parse(uri), map);
            dilutionsInstrument.dilutions(httpProtocolManager,config);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(dilutionsInstrument.getDilutionsGlobalListener() != null){
            dilutionsInstrument.getDilutionsGlobalListener().onUnSupportUri(uri);
        }
        return false;
    }
}

