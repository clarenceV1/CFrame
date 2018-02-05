package com.cai.work.ui.presenter;

import com.cai.framework.base.BaseView;
import com.cai.work.bean.Weather;

/**
 * Created by clarence on 2018/1/12.
 */

public interface MainView extends BaseView {
    void setMainContent(String content);

    void showWeather(Weather weather);
}
