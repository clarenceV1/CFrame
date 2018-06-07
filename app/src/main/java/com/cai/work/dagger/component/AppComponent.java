package com.cai.work.dagger.component;

import com.cai.framework.dagger.module.FrameWorkModule;
import com.cai.work.dagger.module.AppModule;
import com.cai.work.ui.listview.ListviewScollActivity;
import com.cai.work.ui.main.MainActivity;
import com.cai.work.ui.main.MainPresenter;
import com.cai.work.ui.main.fragment.MainHomeFragment;
import com.cai.work.ui.main.fragment.MainMineFragment;
import com.cai.work.ui.web.WebActivity;

import dagger.Component;

/**
 * Created by clarence on 2018/3/26.
 */
@Component(modules = {AppModule.class, FrameWorkModule.class})
public interface AppComponent {

    void inject(MainPresenter mainPresenter);

    void inject(MainActivity mainActivity);

    void inject(MainMineFragment mainMineFragment);

    void inject(MainHomeFragment mainHomeFragment);

    void inject(WebActivity webActivity);

    void inject(ListviewScollActivity listviewScollActivity);
}
