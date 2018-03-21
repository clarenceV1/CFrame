package com.cai.work.ui.presenter;

import android.widget.Toast;

import com.cai.annotation.apt.InstanceFactory;
import com.cai.framework.base.GodBasePresenter;
import com.cai.framework.http.Api;
import com.cai.framework.store.StoreFactory;
import com.cai.framework.store.base.StoreType;
import com.cai.work.ApiService;
import com.cai.work.base.App;
import com.cai.work.bean.Weather;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

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
    @Override
    public void onAttached() {
        TestSaveData();
        requestWeather();
        testSQL();
    }

    private void testSQL() {

    }

    private void TestSaveData() {
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                StoreFactory.getInstance(StoreType.SHARED_PREFERENCE).write("MainPresenter", "我是首页");
                String title = StoreFactory.getInstance(StoreType.SHARED_PREFERENCE).read("MainPresenter", "kong");
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
            String city = URLEncoder.encode("北京", "utf-8");
            Disposable disposable = Api.getInstance().request().create(ApiService.class).getWeather(city)
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<Weather>() {
                        @Override
                        public void accept(Weather user) throws Exception {
                            mView.showWeather(user);
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {
                            Toast.makeText(App.getAppContext(), "请求失败", Toast.LENGTH_SHORT).show();
                        }
                    });
            mCompositeSubscription.add(disposable);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
