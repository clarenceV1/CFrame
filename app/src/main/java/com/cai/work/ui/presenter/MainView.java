package com.cai.work.ui.presenter;

import com.cai.framework.base.GodBaseView;
import com.cai.work.bean.Weather;

/**
 * Created by clarence on 2018/1/12.
 */

public interface MainView extends GodBaseView {
    void setMainContent(String content);

    void showWeather(Weather weather);
}
