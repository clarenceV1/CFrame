package com.cai.work.base;

import android.content.Context;

import com.umeng.analytics.MobclickAgent;

import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class StatisticsPresenter {
    private Context context;

    @Inject
    public StatisticsPresenter() {
        context = App.getAppContext();
    }

    private void mobclickAgent(String eventId) {
        MobclickAgent.onEvent(context, eventId);
    }

    private void mobclickAgent(String eventId, Map<String, String> map) {
        MobclickAgent.onEvent(context, eventId, map);
    }

    private void mobclickAgent(String eventId, String label) {
        MobclickAgent.onEvent(context, eventId, label);
    }

    /**
     * 福利点击
     */
    public void home_fl() {
        mobclickAgent("home-fl");
    }

    /**
     * 首页福利banner点击
     */
    public void home_gdfl() {
        mobclickAgent("home-gdfl");
    }

    /**
     * 糖果领取
     */
    public void home_lq() {
        mobclickAgent("home-lq");
    }

    /**
     * 领取更多
     */
    public void home_lqgd() {
        mobclickAgent("home-lqgd");
    }

    /**
     * 糖果详情
     */
    public void home_tgxq() {
        mobclickAgent("home-tgxq");
    }

    /**
     * 我的资产
     */
    public void home_wdzc() {
        mobclickAgent("home-wdzc");
    }

    /**
     * 往期福利列表点击
     */
    public void gdfl_banner() {
        mobclickAgent("gdfl-banner");
    }

    /**
     * 我的资产--列表点击
     */
    public void wdzc_jl() {
        mobclickAgent("wdzc-jl");
    }

    /**
     * 糖果详情-记录
     */
    public void tgxq_jl() {
        mobclickAgent("tgxq-jl");
    }

    /**
     * 发现页-列表
     */
    public void fx_banner() {
        mobclickAgent("fx-banner");
    }

    /**
     * tab-发现
     */
    public void tab_fx() {
        mobclickAgent("tab-fx");
    }

    /**
     * tab-首页
     */
    public void tab_home() {
        mobclickAgent("tab-home");
    }

    /**
     * tab-我的
     */
    public void tab_wd() {
        mobclickAgent("tab-wd");
    }

    /**
     * 我的-分享
     */
    public void fx_fx(String shareType) {
        mobclickAgent("fx-fx", shareType);
    }

    /**
     * 我的-复制口令
     */
    public void fx_fzkl() {
        mobclickAgent("fx-fzkl");
    }
}
