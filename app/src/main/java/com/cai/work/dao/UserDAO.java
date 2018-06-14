package com.cai.work.dao;

import android.annotation.SuppressLint;

import com.cai.work.bean.User;

import java.util.List;

import javax.inject.Inject;

import io.objectbox.Box;
import io.objectbox.android.AndroidScheduler;
import io.objectbox.query.Query;
import io.objectbox.reactive.DataObserver;
import io.objectbox.reactive.DataSubscription;
import io.objectbox.rx.RxQuery;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class UserDAO extends BaseDAO {

    @Inject
    public UserDAO() {
    }

    public void save(User account) {
        Box<User> box = boxStore.boxFor(User.class);
        if (account != null) {
            box.removeAll();
        }
        box.put(account);
    }

    public User getData() {
        Box<User> box = boxStore.boxFor(User.class);
        return box.query().build().findFirst();
    }

    public void deleteAll() {
        Box<User> box = boxStore.boxFor(User.class);
        box.removeAll();
    }

    public DataSubscription getBalance(DataObserver<List<User>> observer) {
        Box<User> box = boxStore.boxFor(User.class);
        DataSubscription subscription = box.query().build().subscribe().on(AndroidScheduler.mainThread()).observer(observer);
        return subscription;
    }

    public Disposable getBalance(Consumer onNext, Consumer<Throwable> onError) {
        Box<User> box = boxStore.boxFor(User.class);
        Query<User> query = box.query().build();
        Disposable subscribe = RxQuery.observable(query).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread()).map(new Function<List<User>, User>() {
                    @Override
                    public User apply(List<User> users) {
                        if (users != null && users.size() > 0) {
                            return users.get(0);
                        }
                        return new User();
                    }
                }).subscribe(onNext, onError);
        return subscribe;
    }
}
