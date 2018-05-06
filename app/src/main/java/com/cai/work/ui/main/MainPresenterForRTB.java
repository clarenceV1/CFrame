package com.cai.work.ui.main;

import com.cai.framework.base.BaseLifecycleObserver;
import com.cai.framework.base.GodBasePresenter;
import com.cai.framework.protocol.ProtocolInterpreter;
import com.cai.lib.logger.Logger;
import com.cai.work.bean.User;
import com.cai.work.bean.Weather;
import com.cai.work.common.DataStore;
import com.cai.work.common.RequestStore;
import com.cai.work.dagger.component.DaggerAppComponent;
import com.cai.work.dao.UserDAO;
import com.cai.work.protocol.IAModule2App;
import com.example.clarence.imageloaderlibrary.ILoadImageParams;
import com.example.clarence.imageloaderlibrary.ImageForGlideParams;

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
public class MainPresenterForRTB extends GodBasePresenter<MainViewForRTB> {
    @Inject
    DataStore dataStore;
    @Inject
    RequestStore requestStore;
    @Inject
    UserDAO userDAO;

    @Inject
    public MainPresenterForRTB() {
    }

    @Override
    public void onAttached() {
        DaggerAppComponent.create().inject(this);
        TestSaveData();
        requestWeather();
        addUser();
        showImage();
        testLog();
        testProtocol();
    }

    private void testProtocol() {
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) {
                String result = ProtocolInterpreter.getDefault().create(IAModule2App.class).testProtocol(1);
                e.onNext(result);
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String result) {
                        mView.showWeatherError(result);
                    }
                });
    }

    private void testLog() {
        Logger.d("我是的的xxxxggggg");
    }

    private void showImage() {
        ILoadImageParams imageParams = new ImageForGlideParams.Builder()
                .url("http://img5.imgtn.bdimg.com/it/u=269889177,603310778&fm=27&gp=0.jpg")
                .build();
        mView.showImage(imageParams);
    }

    private void addUser() {
        Observable observable = Observable.create(new ObservableOnSubscribe<List<User>>() {
            @Override
            public void subscribe(ObservableEmitter<List<User>> e) {
                e.onNext(getUserData());
            }
        });
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<User>>() {
                    @Override
                    public void accept(List<User> users){
                        mView.showWeatherError(users.toString());
                    }
                });
    }

    public List<User> getUserData() {
        User user = new User();
        user.setName("name_" + 1);
        userDAO.addUser(user);
        List<User> users = userDAO.getUsers();
        return users;
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
                        mView.showWeatherError(title);
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
