package com.cai.work.dagger.component;

import com.cai.work.ui.presenter.MainPresenter;

import dagger.Component;

/**
 * Created by clarence on 2018/3/26.
 */
@Component
public interface AppComponent {

    void inject(MainPresenter mainPresenter);
}
