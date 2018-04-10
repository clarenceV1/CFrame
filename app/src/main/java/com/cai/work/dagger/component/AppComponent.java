package com.cai.work.dagger.component;

import com.cai.framework.dagger.module.FrameWorkModule;
import com.cai.work.dagger.module.AppModule;
import com.cai.work.ui.MainActivity;
import com.cai.work.ui.presenter.MainPresenter;

import dagger.Component;

/**
 * Created by clarence on 2018/3/26.
 */
@Component(modules = {AppModule.class, FrameWorkModule.class})
public interface AppComponent {

    void inject(MainPresenter mainPresenter);

    void inject(MainActivity mainActivity);
}
