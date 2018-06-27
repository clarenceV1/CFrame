package com.cai.work.dagger.component;

import com.cai.framework.dagger.component.FrameWorkComponent;
import com.cai.framework.dagger.module.FrameWorkModule;
import com.cai.work.dagger.module.AppModule;
import com.cai.work.ui.main.MainActivity;
import com.cai.work.ui.main.MainPresenter;
import com.cai.work.ui.web.WebActivity;
import com.cai.work.ui.welcome.WelcomeActivity;
import com.cai.work.ui.welcome.WelcomePresenter;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by clarence on 2018/3/26.
 */
@Singleton
@Component(modules = {FrameWorkModule.class,AppModule.class})
public interface AppComponent extends FrameWorkComponent{
    void inject(WebActivity webActivity);

    void inject(WelcomeActivity activity);

    void inject(WelcomePresenter presenter);

    void inject(MainActivity activity);

    void inject(MainPresenter presenter);
}
