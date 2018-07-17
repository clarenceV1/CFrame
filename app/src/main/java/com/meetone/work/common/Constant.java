package com.meetone.work.common;

import android.content.Context;
import android.content.SharedPreferences;

import com.meetone.work.base.App;

public class Constant {
    public static boolean IS_DEBUG = true;

    //用于WebActivity页面
    public static final String DOMAIN_NAME = "more.ethte.com";
    public static final String H5_NAME = "http://more.one/h5";
    public static final String H5_CANDY = "https://myeoscandy.com";
    public static final String TEST_IP = "101.37.146.65";

    public static final String OFFICIAL_BASE_URL = "https://more.ethte.com/";
    public static final String TEST_BASE_URL = "http://101.37.146.65/";


    private static final String IS_TEST_ENV = "is_test_env";//是否是测试环境

    /**
     * 设置是否是测试环境
     *
     * @param isTest
     */
    public static void saveTestEnv(boolean isTest) {
        SharedPreferences preferences = App.getAppContext().getSharedPreferences("default", Context.MODE_PRIVATE);
        preferences.edit().putBoolean(IS_TEST_ENV, isTest).commit();
    }

    /**
     * 设置是否是测试环境
     *
     * @return
     */
    public static boolean isTestEnv() {
        SharedPreferences preferences = App.getAppContext().getSharedPreferences("default", Context.MODE_PRIVATE);
        return preferences.getBoolean(IS_TEST_ENV, false);
    }
}