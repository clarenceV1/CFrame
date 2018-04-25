package com.cai.work.ui.main;

import com.cai.framework.base.GodBaseView;
import com.cai.work.bean.Weather;
import com.example.clarence.imageloaderlibrary.ILoadImageParams;

/**
 * Created by clarence on 2018/1/12.
 */

public interface MainViewForRTB extends GodBaseView {

    void showWeather(Weather weather);

    void showWeatherError(String error);

    void showImage(ILoadImageParams imageParams);
}
