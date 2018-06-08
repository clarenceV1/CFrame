package com.cai.work.ui.welcome;

import com.cai.framework.base.GodBasePresenter;
import com.cai.work.bean.home.HomeData;
import com.cai.work.common.DataStore;
import com.cai.work.common.RequestStore;
import com.cai.work.dagger.component.DaggerAppComponent;
import com.cai.work.dao.UserDAO;
import com.cai.work.ui.main.MainView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by clarence on 2018/1/12.
 */
public class WelcomePresenter extends GodBasePresenter<WelcomeView> {
    @Inject
    DataStore dataStore;
    @Inject
    RequestStore requestStore;
    @Inject
    UserDAO userDAO;

    @Inject
    public WelcomePresenter() {

    }

    @Override
    public void onAttached() {
        DaggerAppComponent.create().inject(this);
    }
    public void loadData() {
        try {
            Disposable disposable = requestStore.requestHomeData(new Consumer<HomeData>() {
                @Override
                public void accept(HomeData data) {

                }
            }, new Consumer<Throwable>() {
                @Override
                public void accept(Throwable throwable) {

                }
            });
            mCompositeSubscription.add(disposable);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
