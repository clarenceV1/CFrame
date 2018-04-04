package com.cai.work.ui.presenter;

import com.cai.annotation.apt.InstanceFactory;
import com.cai.framework.base.BaseLifecycleObserver;
import com.cai.framework.base.GodBasePresenter;
import com.cai.work.bean.User;
import com.cai.work.bean.Weather;
import com.cai.work.common.DataStore;
import com.cai.work.common.ImageStore;
import com.cai.work.common.RequestStore;
import com.cai.work.dagger.component.DaggerAppComponent;
import com.cai.work.dao.UserDAO;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by clarence on 2018/1/12.
 */
@InstanceFactory
public class MainPresenter extends GodBasePresenter<MainView> {
    @Inject
    DataStore dataStore;
    @Inject
    RequestStore requestStore;
    @Inject
    ImageStore imageStore;
    @Inject
    UserDAO userDAO;

    @Override
    public void onAttached() {
        DaggerAppComponent.create().inject(this);
        TestSaveData();
        requestWeather();
        addUser();
    }

    private void addUser() {
        Observable.create(new ObservableOnSubscribe<List<User>>() {
            @Override
            public void subscribe(ObservableEmitter<List<User>> e) throws Exception {
                User user = new User();
                user.setName("name_" + 1);
                userDAO.addUser(user);
                List<User> users = userDAO.getUsers();
                e.onNext(users);
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<User>>() {
                    @Override
                    public void accept(List<User> users) throws Exception {
                        mView.showWeatherError(users.toString());
                    }
                });
    }

    @Override
    public void ON_CREATE() {
        data.put(BaseLifecycleObserver.CLASS_NAME, "MainActivity");
        super.ON_CREATE();
    }

    @Override
    public void ON_PAUSE() {
        super.ON_PAUSE();

    }

    private void TestSaveData() {
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                dataStore.setTitle("MainPresenter", "我是首页2");
                String title = dataStore.getTitle("MainPresenter", "kong");
                e.onNext(title);
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String title) throws Exception {
                        mView.setMainContent(title);
                    }
                });
    }

    public void requestWeather() {
        try {
            Disposable disposable = requestStore.requestWeather("北京", new Consumer<Weather>() {
                @Override
                public void accept(Weather user) throws Exception {
                    mView.showWeather(user);
                }
            }, new Consumer<Throwable>() {
                @Override
                public void accept(Throwable throwable) throws Exception {
                    mView.showWeatherError("请求失败");
                }
            });
            mCompositeSubscription.add(disposable);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
